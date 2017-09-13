# Document

浏览器历史

Modern browsers let JavaScript manipulate the URL without making an http request, so we don't need to rely on the hash (#) portion of the url to do routing, but there's a catch (we'll get to it later).

Your server needs to deliver your app no matter what URL comes in, because your app, in the browser, is manipulating the URL. Our current server doesn't know how to handle the URL.

如何设置以/admin开头的url：404s fallback to /admin

We also need to change our relative paths to absolute paths in index.html since the URLs will be at deep paths and the app, if it starts at a deep path, won't be able to find the files.

## 前端开发
```shell
npm install
npm run-script watch
```