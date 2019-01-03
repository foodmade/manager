<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2018/7/17
  Time: 下午11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>code</title>
</head>
<body>
<div class="form-group  col-lg-6">
    <label for="id" class="col-sm-4 control-label">
        验证码:
    </label>
    <div class="col-sm-8">
        <input type="text" id="code" name="code" class="form-control" style="width:250px;"/>
        <img id="imgObj" alt="验证码" src="data:image/png;base64,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\r\nd/Wb5D9whQp1CuJk/wAAAABJRU5ErkJggg==" onclick="changeImg()"/>
        <a href="#" onclick="changeImg()">换一张</a>
    </div>
</div>

<script type="text/javascript">
    // 刷新图片
    function changeImg() {
        var imgSrc = $("#imgObj");
        var src = imgSrc.attr("src");
        imgSrc.attr("src", changeUrl(src));
    }
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    function changeUrl(url) {
        var timestamp = (new Date()).valueOf();
        var index = url.indexOf("?",url);
        if (index > 0) {
            url = url.substring(index, url.indexOf(url, "?"));
        }
        if ((url.indexOf("&") >= 0)) {
            url = url + "×tamp=" + timestamp;
        } else {
            url = url + "?timestamp=" + timestamp;
        }
        return url;
    }
</script>
</body>
</html>
