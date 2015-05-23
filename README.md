# OdinHttp
Odin is a lightweight Http Library wich simplifies the android HTTP Communication

### Simple GET Request:

```java
        new GETRequest().setURL("http://www.google.de").execute(new RequestCallback() {
            @Override
            public void onFinish(OdinResponse response) {
                Log.i("SUCCESS", response.getResponseBody());
            }

            @Override
            public void onError(String error) {
                Log.i("ERROR", error);

            }
        });
```

### Simple POST Request, supports automatic Mapping using Gson

The response from the sample URL look like this:
```json
  {
    "one": "two",
    "key": "value"
  }
```

```java
    new POSTRequest().setURL("http://echo.jsontest.com/key/value/one/two").executeJSONMapping(new IOdinJSONMapping<OnTwo>() {
            @Override
            public void onFinish(OnTwo object) {
                String json = new Gson().toJson(object,OnTwo.class);
                Log.i("JSON",json);
            }

            @Override
            public void onError(String error) {

            }
        },OnTwo.class);
```

