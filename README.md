# file-appender-batch

---------------------

This project was made to validate filesystem concurrent when multiples instances appent to the same file and validate in the end to check if some
append request failed.

The code was made to simple filesystem, that can be used to PVC in K8S, and to FTP.

## Test simple filesystem in K8S/PVC

Execute commands from "./local-env/k8s" directory.

### Iniciar

```shell
./start.sh
./stop.sh
```

### Remover recursos

```shell
./stop.sh
```

