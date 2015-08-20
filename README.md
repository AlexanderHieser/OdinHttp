# OdinHttp
Odin is a lightweight, completely asynchronous, Http Library wich simplifies android HTTP Communication.

### Gradle 

```java
	
	compile(group: 'de.ahieser.odinhttp', name: 'odin', version: '0.5.2', ext: 'aar')
	
```



### Simple GET Request:

```java

	Odin odin = new Odin();
        odin.Get().setURL("https://www.google.de/").execute(new StringCallback() {
            @Override
            public void onFinish(OdinResponse response) {
                Log.i("TEST",response.getResponseBody());
            }

            @Override
            public void onError(String error) {
                Log.i("TEST",error);

            }
        });

```

The onFinisch Method runs on the UI Thread, so you can update your UI without any trouble.

### Simple POST Request, supports automatic Mapping using Gson

The response from the sample URL look like this:

```json
  {
    "one": "two",
    "key": "value"
  }
```

```java

    odin.Post().setURL("http://echo.jsontest.com/key/value/one/two")
					 .executeJSONMapping(OnTwo.class,new JSONCallback<OnTwo>() {
            @Override
            public void onFinish(OnTwo object) {
                String json = new Gson().toJson(object,OnTwo.class);
                Log.i("JSON",json);
            }

            @Override
            public void onError(String error) {
				Log.i("TAG!", error);
            }
        });
```

### Also CustomCallbacks are supported

```java

   new odin.Get()..setURL("http://www.google.de")
				   .execute(OnTwo.class, new CustomCallback<OnTwo>() {
            @Override
            public void onFinish(OnTwo object) {
                // Get your prepared Object 
            }

            @Override
            public void onError(String error) {
				Log.e("TAG",error);
            }

            @Override
            public OnTwo onPrepare(HttpResponse response) {
				//DO what ever u want :D
            }
        });
    }

```