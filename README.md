# Vothemis
Simple and Standalone Voting Api

# Getting started
```
git clone https://github.com/awataki/Vothemis
docker-compose up -d //Start postgresql
./gradlew run // Start Vothemis Server
```

# Configration
Vothemis/Infra/resources/application.conf

```
ktor {
  deployment {
    // listeing port
    port = 9841
    port = ${?PORT}
  }
  application {
    modules = [sh.awtk.vothemis.ApplicationKt.module]
  }
}

vothemis {
  jwt {
    // jwt iss claim
    issuer = "https://vothemis.awtk.sh"
    // jwt realm
    realm = "Vothemis"
    // jwt aud claim
    audience = "vothemis-app"
    // jwt secretkey
    secret = "test"
  }
  db {
    driverClassName = org.postgresql.Driver
    // jdbcURL 
    jdbcUrl = "jdbc:postgresql://localhost:5432/vothemis?useSSL=false"
    // db username
    username = "testuser"
    // db password
    password = "testuser"
    // Max connection pool size
    maximumPoolSize = 20
    isAutoCommit = false
    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
  }
}

```

# API Reference
Please see `Vothemis.v1.yaml`
