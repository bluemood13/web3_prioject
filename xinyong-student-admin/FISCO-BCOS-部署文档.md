# FISCO BCOS 本地节点部署文档

> 部署时间：2026-03-27  
> 部署人：AI 自动化部署  
> 文档版本：v1.0

---

## 一、环境信息

| 项目 | 值 |
|---|---|
| 宿主机系统 | Windows 10 |
| 虚拟机平台 | VMware Workstation |
| 虚拟机系统 | Ubuntu 25.10 (questing) |
| 虚拟机 IP | 192.168.135.129 |
| 虚拟机账号 | amiono / 123456 |
| Java 版本 | OpenJDK 11.0.30 |
| FISCO BCOS 版本 | **v2.11.1** |
| 节点数量 | 1 节点（开发模式） |

---

## 二、节点部署信息

### 2.1 节点目录结构

```
/home/amiono/fisco/
├── build_chain.sh          # 建链脚本（来自 Gitee 镜像）
├── fisco-bcos              # FISCO BCOS 可执行文件
└── nodes/
    ├── cert/               # 链级 CA 证书
    └── 192.168.135.129/    # 节点目录
        ├── node0/          # 节点 0
        │   ├── conf/       # 节点证书和配置
        │   ├── config.ini  # 节点主配置
        │   ├── log/        # 运行日志
        │   └── data/       # 账本数据
        ├── sdk/            # SDK 连接证书（已复制到项目）
        │   ├── ca.crt
        │   ├── sdk.crt
        │   └── sdk.key
        ├── start_all.sh    # 一键启动
        └── stop_all.sh     # 一键停止
```

### 2.2 端口说明

| 端口 | 协议 | 用途 | 外部可访问 |
|---|---|---|---|
| **20200** | Channel（TLS） | Java SDK 连接入口 | ✅ 是 |
| 30300 | P2P | 节点间通信 | ✅ 是 |
| 8545 | HTTP JSON-RPC | 本地 RPC 调试 | ❌ 仅 VM 本机 |

---

## 三、项目配置变更

### 3.1 application.yml 改动

```yaml
# 切换为本地节点
qkl:
  value: 'fisco-local'    # 原来是 'fisco-remote'

# 更新本地节点地址
fisco-local:
  peers: 192.168.135.129:20200   # 原来是 192.168.2.100:20200
  groupId: 1
  certPath: fisco/conf/local
```

### 3.2 SDK 证书位置（已自动复制）

```
admin/src/main/resources/fisco/conf/local/
├── ca.crt      # 链级 CA 证书（1139 bytes）
├── sdk.crt     # SDK 客户端证书（1192 bytes）
├── sdk.key     # SDK 客户端私钥（1704 bytes）
└── acount.p12  # 交易签名账户密钥（900 bytes）
```

> **注意**：以上证书是与已部署节点配套生成的，请勿与其他节点的证书混用。

---

## 四、节点管理命令

以下命令均在 Ubuntu VM（192.168.135.129）上执行。

### 4.1 通过 SSH 连接 VM

```bash
ssh amiono@192.168.135.129
# 密码：123456
```

### 4.2 节点启动 / 停止 / 状态

```bash
# 启动所有节点
bash /home/amiono/fisco/nodes/192.168.135.129/start_all.sh

# 停止所有节点
bash /home/amiono/fisco/nodes/192.168.135.129/stop_all.sh

# 查看节点进程
ps aux | grep fisco-bcos | grep -v grep

# 查看节点日志（实时）
tail -f /home/amiono/fisco/nodes/192.168.135.129/node0/log/log_*.log
```

### 4.3 验证节点正常运行

```bash
# 通过 JSON-RPC 查询当前区块号（在 VM 内执行）
curl -X POST --data '{"jsonrpc":"2.0","method":"getBlockNumber","params":[1],"id":1}' \
  http://127.0.0.1:8545

# 预期返回示例：
# {"id":1,"jsonrpc":"2.0","result":"0x5"}
```

节点日志中出现以下内容说明节点正常运行中：
```
[CONSENSUS][SEALER]++++++++++++++++ Generating seal on,blkNum=...
```

### 4.4 systemd 服务（开机自启）

```bash
# 查看服务状态
systemctl status fisco-bcos.service

# 手动启动服务
sudo systemctl start fisco-bcos.service

# 停止服务
sudo systemctl stop fisco-bcos.service

# 查看是否开机自启
systemctl is-enabled fisco-bcos.service
```

---

## 五、常见问题排查

### Q1: 项目启动报 "connection timed out" 连接 20200 端口

**排查步骤：**
1. 确认 VM 正在运行（VMware 中检查）
2. 确认节点进程在运行：
   ```bash
   ssh amiono@192.168.135.129 "ps aux | grep fisco-bcos | grep -v grep"
   ```
3. 在 Windows 上测试端口连通性：
   ```powershell
   Test-NetConnection 192.168.135.129 -Port 20200
   ```
4. 若节点未运行，手动启动：
   ```bash
   ssh amiono@192.168.135.129 "bash /home/amiono/fisco/nodes/192.168.135.129/start_all.sh"
   ```

### Q2: 项目启动后区块链功能返回 "区块链节点不可用"

这是正常的懒加载机制。第一次调用区块链接口时会真正建立连接（可能等待 3-10 秒），之后恢复正常。若一直失败，参考 Q1 排查。

### Q3: 证书认证失败 (SSL/TLS 相关错误)

证书是与节点一一绑定的。如果重新执行了 `build_chain.sh` 建链，需要重新从 VM 复制证书：

```python
# 在 Windows 上执行（需要 paramiko 库）
import paramiko, os
c = paramiko.SSHClient()
c.set_missing_host_key_policy(paramiko.AutoAddPolicy())
c.connect('192.168.135.129', username='amiono', password='123456')
sftp = c.open_sftp()
dst = r"admin\src\main\resources\fisco\conf\local"
os.makedirs(dst, exist_ok=True)
for f in ['ca.crt', 'sdk.crt', 'sdk.key']:
    sftp.get(f'/home/amiono/fisco/nodes/192.168.135.129/sdk/{f}', f'{dst}/{f}')
    print(f'Downloaded {f}')
sftp.close(); c.close()
```

### Q4: 如何切回远程生产节点

修改 `admin/src/main/resources/application.yml`：
```yaml
qkl:
  value: 'fisco-remote'   # 改回 fisco-remote
```

---

## 六、SDK 版本兼容性说明

| 组件 | 版本 |
|---|---|
| FISCO BCOS 节点 | v2.11.1 |
| fisco-bcos-java-sdk | v2.9.1 |

FISCO BCOS Java SDK 2.9.x 与节点 2.11.x 在同一 2.x 大版本内**向后兼容**，可正常使用。Channel 协议版本一致，交易格式兼容。

---

## 七、网络架构示意

```
Windows 宿主机 (开发机)
├── IntelliJ IDEA / Maven
│   └── Spring Boot 应用 (port 9999)
│       └── fisco-bcos-java-sdk
│           └── Channel TLS 连接
│               │
│               ▼ 192.168.135.129:20200
VMware NAT 网络
│
└── Ubuntu VM (192.168.135.129)
    └── FISCO BCOS v2.11.1
        └── node0
            ├── Channel Port: 20200  ← SDK 连接入口
            ├── P2P Port:     30300
            └── RPC Port:     8545 (仅本地)
```

---

*文档自动生成 by Cursor AI Agent · 2026-03-27*
