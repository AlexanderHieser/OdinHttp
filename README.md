# OdinHttp
Odin is a lightweight Http Library wich simplifies the android HTTP Communication

### Simplete GET Request:

```java
  final WebView view = (WebView) findViewById(R.id.web);
        new OdinRequest().setRequestURL("http://www.google.de")
                         .setMethodType("GET")
                         .execute(new OdinInterface() {
            @Override
            public void onFinish(OdinResponse response) {
                view.loadData(response.getResponseBody(), "text/html", "UTF-8");
            }

            @Override
            public void onError(String error) {
                Log.e("Error",error);
            }
        });
```
