<?php
// Notes:
// in my.cnf under [mysqld] i had to set max_allowed_packet = 128M
// or whatever size the max import.sql will be

/**
 * Ensures the name, ip, and guid are sane.
 */
function isValidData($pairs) {
	// ensure ip, guid keys exist
	if (!array_key_exists('ip', $pairs)) {
		return false;
	}
	else if (!array_key_exists('cl_guid', $pairs)) {
		return false;
	}
	// then ensure each one is sane
	else if(!preg_match("/^(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3}):\d{1,5}$/",$pairs['ip'])) {
		return false;
	}
	else if (!preg_match("/^[a-zA-Z0-9]{32}$/",$pairs['cl_guid'])) {
		return false;
	}
	else {
		return true;
	}
}

/**
 * Gets the IP of the server from the log filename.
 *
 * @param string $code One of: ctf, dom, ffa, fun, icy, jum, pub, xma, zom, sup, ets
 */
function getServerIP($code) {
	if ($code == 'ctf')
		return '208.43.15.82';
	else if ($code == 'dom')
		return '208.43.15.85';
	else if ($code == 'ffa')
		return '208.43.15.90';
        else if ($code == 'fun')
		return '208.43.15.81';
	else if ($code == 'icy')
		return '208.43.15.8';
	else if ($code == 'jum')
		return '208.43.15.83';
	else if ($code == 'pub')
		return '208.43.15.202';
	else if ($code == 'xma')
		return '208.43.15.89';
	else if ($code == 'zom')
		return '208.43.15.108';
	else if ($code == 'sup')
		return '208.43.15.102';
	else if ($code == 'ets')
		return 'IGNORE';
	else
		return false;
}

// Prepare SQL dump file:
$fh = fopen('import.sql', 'w') or die("\nFailed to Open SQL File for Writing.\n");
fwrite($fh, "INSERT IGNORE INTO `1up_players` (`name`, `ip`, `guid`, `server`, `logdate`) VALUES\n");

foreach (new DirectoryIterator('.') as $fileInfo) {
	$filename = $fileInfo->getFilename();
	// Get Server IP from Filename
	$server  = getServerIP(substr($filename, 12, 3));
	if ($fileInfo->isFile() && substr($filename, -4) == '.log' && $server != 'IGNORE') {

		$logdate = substr($filename, 0, 11);
		$logdate = DateTime::createFromFormat('M_d_Y', $logdate);
		$logdate = $logdate->getTimestamp();

		if (!$server) {
			die("\nFailed to get the server for ".$filename."\n");
		}

		$lines = file($fileInfo->getFilename());
		foreach ($lines AS $line) {
			// if line contains ClientUserinfo:
			if (strpos($line, 'ClientUserinfo:') !== false) {
				$data = explode('\\', $line); // Key/Value pairs delimited by backslash
				array_shift($data); // delete $data[0] basically.
				$data = implode('\\', $data); // convert back into string delimited by backslash
				// Convert string to key/value pairs as associative array.
				preg_match_all("/([^\\\\]+)\\\\([^\\\\]+)/", $data, $pairs);
				$pairs = array_combine($pairs[1], $pairs[2]);
				// Ensure the data is sane and is all there.
				if (isValidData($pairs)) {
					if (array_key_exists('name', $pairs)) {
						if (empty($pairs['name'])) {
							$name = "UnnamedPlayer";
						} else {
							$name = mysql_real_escape_string(trim($pairs['name']));
						}
					} else {
						$name = "UnnamedPlayer";
					}
					$ip   = trim($pairs['ip']);
					$ip   = explode(':', $ip);
					$ip   = $ip[0];
					$guid = trim($pairs['cl_guid']);
					// Write data to SQL file
					fwrite($fh, "('$name', INET_ATON(\"$ip\"), '$guid', INET_ATON(\"$server\"), '$logdate'),\n");
				} else {
					// ClientUserinfo string doesnt have name, ip, guid, or they arent sane.
					if (!array_key_exists('characterfile', $pairs)) {
						echo $line."\n";
					}
					// otherwise its a 1up Bot, and we dont care.
				}
			}
		}
	}
}
// Replace the last character in the file which is a comma with a semicolon.
fseek($fh, -2, SEEK_END);
fwrite($fh, ";");
// Release memory back to the OS.
fclose($fh);

?>
