# Remote Tax Preparation System
The capstone project for MCL Taxes created by Kerk Day, Jesse Goerzen, Cesar Guzman, Taylor Hanlon, and Tristen Kreutz

## Installation
On windows do the following to install the system.
1. Install MySQL.
2. Run the following command from within MySQL with administrator privileges.
```sql
source 
```
3. Install Apache Tomcat, or another java-based web-server onto your server platform. 
4. Unzip wcl_remote_tax_prep.zip in the folder \[Catalina Home\].
5. Run \[Catalina Home\]\\temp\\setup.bat
6. Edit \[Catalina Home\]\\conf\\wcl\\config.txt
    1. Try somethings
7. Run \[Catalina Home\]\\bin\\startup.bat
8. On your browser, go to the following link
http://localhost:8080/manager/text/deploy?war=wcl
