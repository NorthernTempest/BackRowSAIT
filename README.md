# Remote Tax Preparation System
The capstone project for MCL Taxes created by Kerk Day, Jesse Goerzen, Cesar Guzman, Taylor Hanlon, and Tristen Kreutz

## Windows
### Installation Instructions
1. Install MySQL version 5.7 or later.
2. Install Apache Tomcat version 9.0 or later.
3. Unzip 'wcl_remote_tax_prep.zip' in the directory '\[Catalina Home\]' which is the directory you unzipped in the installation of Apache Tomcat.
4. Run '\[Catalina Home\]\\wcl\\TaxPreparer_Database_Create.sql' from within MySQL with administrator privileges.
5. Run '\[Catalina Home\]\\wcl\\Database_User_Create.sql' from within MySQL with administrator privileges.
6. Unzip '\[Catalina Home\]\\wcl\\config_files.zip' in any folder of your choice. These files contain private data about your system, so consider placing these files on a removable drive or another. Remember the location where you installed your files
7. Edit '\[Catalina Home\]\\conf\\wcl\\config.txt'
    1. Replace all instances of 'C:\Capstone\TestFiles' with the path where you unzipped 'config_files.zip'.
    2. Replace all instances of 'C:\Capstone\BackRowSAIT\Remote Tax Preparer Web Server\res\' with the path where you unzipped 'config_files.zip'.
    3. Replace all instances of 'C:\\Capstone\\TestFiles' with the path where you unzipped 'config_files.zip', but with double slashes.
    4. Replace all instances of 'C:\Program Files\MySQL\MySQL Server 5.7\' with your MySql Server directory.
8. Edit the files from 'config_files.zip'
    1. Replace the contents of 'emailpassword.txt' with the password to the account you use to email users.
    2. Replace the contents of 'emailusername.txt' with the username to the account you use to email users.
    3. Replace the contents of 'sqladminpassword.txt' with the password to an admin account for MySQL.
    4. Replace the contents of 'sqladminusername.txt' with the username to an admin account for MySQL.
9. Run \[Catalina Home\]\\bin\\startup.bat
10. On your internet browser of choice, go to the following link
http://localhost:8080/manager/text/deploy?war=wcl
    1. If installation and startup is successful, you will receive a response like this:
'OK - Deployed application at context path /wcl'

### Startup Instructions
1. Run \[Catalina Home\]\\bin\\startup.bat
2. On your internet browser of choice, go to the following link
http://localhost:8080/manager/text/start?path=/wcl

### Shutdown Instructions
1. On your internet browser of choice, go to the following link
http://localhost:8080/manager/text/stop?path=/wcl
2. Run \[Catalina Home\]\\bin\\shutdown.bat
