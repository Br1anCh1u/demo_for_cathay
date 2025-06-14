# demo_for_cathay
simple yet fairly complete

####This is a simple yet fairly complete demo that includes:
- Spring Boot for building REST APIs
- Spring Data JPA for database access
- OpenFeign for HTTP client calls
- Sleuth for distributed tracing
- H2 in-memory database for easy demonstration
- JUnit and Mockito for unit testing
- Maven plugins for code quality checks (PMD, Checkstyle, SpotBugs)
- JaCoCo for test coverage reporting


### API Endpoints

- `GET    /api/coinDesk`  
  取得原始的 CoinDesk 資料

- `GET    /api/coinDesk/converted`  
  取得轉換後的 CoinDesk 幣別資料

- `GET    /api/currency?currencyNumber={number}&currencyCode={code}`  
  查詢特定幣別（根據幣別編號與代碼）

- `GET    /api/currency?currencyNumber={number}&currencyCode={code}`  
  查詢幣別（幣別編號與代碼皆為選填）

- `POST   /api/currency`  
  新增幣別資訊  
  Request Body：
  ```json
  {
    "currencyCode": "USD",
    "currencyNumber": "840",
    "currencyCht": "美元"
  }

- `PATCH   /api/currency?currencyNumber={number}&currencyCode={code}`
  更新幣別資訊（依幣別編號或代碼指定）
  Request Body：
  ```json
  {
    "currencyCode": "USD",
    "currencyNumber": "840",
    "currencyCht": "美元"
  }
  
- `DELETE   /api/currency?currencyNumber={number}&currencyCode={code}`
  刪除幣別資訊（依幣別編號或代碼指定）
