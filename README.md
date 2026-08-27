# Vehicle Service

Vehicle CRUD service on `8001`, backed by MySQL on `13500`. New vehicles validate their customer through
`CUSTOMER-SERVICE` via Eureka. Vehicle images are stored locally under `${user.home}/.autocare/vehicles` by default.
