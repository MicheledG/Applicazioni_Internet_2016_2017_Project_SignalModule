# Signal Microservice - Internet Applications Project

The Signal Microservice part of the final project for Internet Applications course held at Polytechnic University of Turin by Prof. Malnati and Prof. Servetti (a.y. 2016/2017).

## Security

This microservice requires authentication for any request. 

Authentication is based on a token (JWT), obtained by providing the right credentials to the Authentication Microservice.

The authentication token must be placed in the http request as `Authorization` header:

`Authorization: Bearer XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	` 

If a wrong authentication token is provided, a `401 Unauthorized` code is returned.

## REST Services

| API	               | Method | Req. body  | Query params | Status | Resp. body | Meaning    					  |
|:--------------------:|:------:|:----------:|:------------:|:------:|:----------:|:-------------------------------|
| `/signals`           | `GET`  |            |              | 200    | `List<Signal>` | Get the signals currently active |
|                      |        |            |              | 204    |            | No signals currently active |
| `/signals`           | `POST` | `SignalDto`  |              | 201    |            | Create a new signal |
|                      |        |            |              | 400    |            | The signal is not valid, bad request |
|                      |        |            |              | 409    |            | A signal for the same coordinates already exists |
| `/ratings`           | `POST` | `RatingDto`  |              | 201    |            | Add a new rating for a given signal |
|                      |        |            |              | 400    |            | The rating is not valid, bad request |
|                      |        |            |              | 403    |            | The signal to rate has expired |
|                      |        |            |              | 404    |            | The signal to rate doesn't exist |
| `/signals/reference` | `POST` |`ReferenceDto`|              | 200    |            | The given signal has been correctly referenced |
|                      |        |            |              | 400    |            | The reference is not valid, bad request |
|                      |        |            |              | 404    |            | The signal to reference doesn't exist (anymore) |

## Web Socket

| Endpoint | `/signal-websocket` |
| Topic    | `/topic/signals`    |
| App      | `/app/signal`       |
| Message  | `Signal`            |