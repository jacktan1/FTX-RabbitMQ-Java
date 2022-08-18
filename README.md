# Price Tracking using RabbitMQ

## Usage

**Pull RabbitMQ image (if needed)**:

```
docker pull rabbitmq:3-management
```

**Start RabbitMQ server on localhost:**

```
docker run -d --rm -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

**RabbitMQ UI:** `http://localhost:15672/`

**Start publisher job:**

```
./mvnw spring-boot:run
```

## Design

### Publisher

- Given a list of tickers, periodically obtain price quotes via sending
  `https://ftx.com/api/markets/{TICKER}` GET requests.
- Publish response to RabbitMQ exchange / queue.

### Tickers

```
"BTC-PERP", "ETH-PERP", "AVAX-PERP", "BNB-PERP", "FTT-PERP", "SOL-PERP",
"ADA-PERP", "ATOM-PERP", "LINK-PERP", "MATIC-PERP", "XMR-PERP", "FTM-PERP",
"NEAR-PERP", "SAND-PERP", "DOT-PERP", "OP-PERP", "AXS-PERP", "APE-PERP",
"WAVES-PERP", "CEL-PERP", "CRV-PERP", "GRT-PERP", "AAVE-PERP", "SNX-PERP"
```