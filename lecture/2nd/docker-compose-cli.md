## Docker Compose CLI Examples

### 1. [Build or rebuild services](https://docs.docker.com/compose/reference/build/)
- **Usage:**
  ```shell
  docker-compose build [options] [--build-arg key=val...] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose build
  ```

### 2. [List images used by the created containers](https://docs.docker.com/compose/reference/images/)
- **Usage:**
  ```shell
  docker-compose images [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose images
  ```

### 3. [Create and start containers](https://docs.docker.com/compose/reference/up/)
- **Usage:**
  ```shell
  docker-compose up [options] [--scale SERVICE=NUM...] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose up
  docker-compose up -d  # Start in detached mode
  ```

### 4. [Start services](https://docs.docker.com/compose/reference/start/)
- **Usage:**
  ```shell
  docker-compose start [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose start
  ```

### 5. [Stop services](https://docs.docker.com/compose/reference/stop/)
- **Usage:**
  ```shell
  docker-compose stop [options] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose stop
  docker-compose stop -t 20  # Stop with a timeout
  ```

### 6. [View output from containers](https://docs.docker.com/compose/reference/logs/)
- **Usage:**
  ```shell
  docker-compose logs [options] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose logs
  docker-compose logs -f  # Follow log output
  ```

### 7. [Remove stopped containers](https://docs.docker.com/compose/reference/rm/)
- **Usage:**
  ```shell
  docker-compose rm [options] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose rm -f
  ```

### 8. [Execute a command in a running container](https://docs.docker.com/compose/reference/exec/)
- **Usage:**
  ```shell
  docker-compose exec [options] SERVICE COMMAND [ARGS...]
  ```
- **Example:**
  ```shell
  docker-compose exec web bash
  ```

### 9. [Manage services](https://docs.docker.com/compose/reference/)
- **General Usage:**
  ```shell
  docker-compose [COMMAND] [SERVICE...]
  ```

### 10. [Pulls service images](https://docs.docker.com/compose/reference/pull/)
- **Usage:**
  ```shell
  docker-compose pull [options] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose pull
  ```

### 11. [Push service images](https://docs.docker.com/compose/reference/push/)
- **Usage:**
  ```shell
  docker-compose push [options] [SERVICE...]
  ```
- **Example:**
  ```shell
  docker-compose push
  ```

### 12. [Login to a Docker registry](https://docs.docker.com/compose/reference/login/)
- **Usage:**
  ```shell
  docker login [OPTIONS] [SERVER]
  ```
- **Example:**
  ```shell
  docker login
  ```

### 13. [Logout from a Docker registry](https://docs.docker.com/compose/reference/logout/)
- **Usage:**
  ```shell
  docker logout [SERVER]
  ```
- **Example:**
  ```shell
  docker logout
  ```

### 14. [Manage Docker system](https://docs.docker.com/compose/reference/)
- **Usage:**
  ```shell
  docker system COMMAND
  ```

### 15. [Manage networks in Docker Compose](https://docs.docker.com/compose/networking/)
- **Usage:**
  ```shell
  docker-compose network [COMMAND]
  ```
- **Example:**
  ```shell
  docker-compose network inspect my-network
  ```
