# WeBASE 环境部署文档

## 服务器信息

| 项目 | 内容 |
|------|------|
| 操作系统 | CentOS 7.9.2009 |
| IP 地址 | 192.168.135.130 |
| 用户名 | amino |
| WeBASE 工作目录 | `/home/amino/webase/` |

---

## 已安装环境清单

| 组件 | 版本 | 状态 |
|------|------|------|
| Java | OpenJDK 11.0.23 (默认) | 已安装 ✓ |
| Python3 | 3.6.8 | 已安装 ✓ |
| Node.js | 16.20.2 | 已安装 ✓ |
| npm | 8.19.4 | 已安装 ✓ |
| MySQL | 5.7.44 | 已安装并运行 ✓ |
| Git | 1.8.3.1 | 已安装 ✓ |

---

## MySQL 配置信息

| 项目 | 内容 |
|------|------|
| 版本 | MySQL 5.7.44 |
| root 账号 | `root` |
| root 密码 | `123456` |
| 监听端口 | `3306` |
| 已创建数据库 | `webase` |
| 远程访问 | 已开启（`root@%`） |
| 开机自启 | 已配置（systemd） |

### MySQL 常用命令

```bash
# 登录 MySQL
mysql -uroot -p'123456'

# 查看数据库
mysql -uroot -p'123456' -e 'SHOW DATABASES;'

# 启动 MySQL
sudo systemctl start mysqld

# 停止 MySQL
sudo systemctl stop mysqld

# 查看运行状态
sudo systemctl status mysqld

# 查看 MySQL 日志
sudo tail -f /var/log/mysqld.log
```

---

## yum 源配置

CentOS 7 官方源已于 2024 年 6 月停止维护，本次已切换为 **阿里云镜像源**。

配置文件位置：`/etc/yum.repos.d/CentOS7-Aliyun.repo`

包含以下仓库：
- `base` → `mirrors.aliyun.com/centos-vault/7.9.2009/os/`
- `updates` → `mirrors.aliyun.com/centos-vault/7.9.2009/updates/`
- `extras` → `mirrors.aliyun.com/centos-vault/7.9.2009/extras/`
- `epel` → `mirrors.aliyun.com/epel/7/`
- `mysql57-community` → `mirrors.aliyun.com/mysql/yum/mysql57-community-el7/`

---

## 防火墙端口开放情况

以下端口已通过 `firewall-cmd` 永久开放：

| 端口 | 用途 |
|------|------|
| 3306 | MySQL |
| 5000 | WeBASE-Front |
| 5001 | WeBASE-Node-Manager |
| 5002 | WeBASE-Sign |
| 5003 | WeBASE-Transaction |
| 8080 | WeBASE-Web 前端 |
| 8545 | 以太坊 RPC（兼容） |
| 20200 | FISCO BCOS Channel |
| 20201 | FISCO BCOS P2P |

---

## 下一步：部署 WeBASE

### 方式一：一键部署（推荐）

```bash
# 切换到 webase 工作目录
cd /home/amino/webase

# 下载 WeBASE 一键部署脚本（Gitee 镜像）
wget https://gitee.com/WeBank/WeBASE/releases/download/v1.5.5/webase-deploy.zip

# 解压
unzip webase-deploy.zip
cd webase-deploy

# 编辑配置文件
vim comm/properties.ini
```

### `properties.ini` 关键配置项

```ini
# MySQL 配置（已与本文档一致）
mysql.ip=127.0.0.1
mysql.port=3306
mysql.user=root
mysql.password=123456
mysql.database=webase

# 节点前置端口
node.frontPort=5002

# 管理平台端口
web.port=5000
```

### 执行部署

```bash
# 部署并启动所有服务
python3 deploy.py installAll

# 查看运行状态
python3 deploy.py status

# 停止所有服务
python3 deploy.py stopAll

# 启动所有服务
python3 deploy.py startAll
```

### 访问地址

部署完成后，通过浏览器访问：

```
http://192.168.135.130:5000/WeBASE-Node-Manager
```

默认管理员账号：`admin` / `Abcd1234`

---

## Java 版本说明

系统同时安装了 Java 8 和 Java 11，当前默认为 **Java 11**（WeBASE 要求）。

```bash
# 查看当前 Java 版本
java -version

# 切换 Java 版本
sudo alternatives --config java
```

---

## 常见问题排查

### MySQL 无法连接
```bash
# 检查服务状态
sudo systemctl status mysqld

# 检查端口监听
netstat -tlnp | grep 3306

# 检查防火墙
sudo firewall-cmd --list-ports
```

### yum 安装失败
```bash
# 清理缓存重试
sudo yum clean all && sudo yum makecache
```

### 端口被占用
```bash
# 查看端口占用
sudo lsof -i :5000
```

---

## 部署时间记录

- 环境部署完成时间：2026-03-28
- 部署人员：自动化脚本（via Cursor AI）
