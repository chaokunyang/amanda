# Installation as an init.d service (System V)
```shell
sudo ln -s /var/myapp/myapp.jar /etc/init.d/myapp
```

debian
```shell
service myapp start
update-rc.d myapp defaults <priority>
```

使用非root用户启动app
```shell
chown bootappuser:bootappuser your-app.jar
```

prevent the modification of your application’s jar file
```shell
chmod 500 your-app.jar
```

If an attacker does gain access, they could make the jar file writable and change its contents. One way to protect against this is to make it immutable using chattr, This will prevent any user, including root, from modifying the jar:
```shell
sudo chattr +i your-app.jar
```


# Installation as a systemd service
Assuming that you have a Spring Boot application installed in /var/myapp, to install a Spring Boot application as a systemd service create a script named myapp.service using the following example and place it in /etc/systemd/system directory:
```
[Unit]
Description=myapp
After=syslog.target

[Service]
User=myapp
ExecStart=/var/myapp/myapp.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```
Note that unlike when running as an init.d service, user that runs the application, PID file and console log file are managed by systemd itself and therefore must be configured using appropriate fields in ‘service’ script. Consult the service unit configuration man page for more details.

To flag the application to start automatically on system boot use the following command:

```
systemctl enable myapp.service
```
Refer to man systemctl for more details.
