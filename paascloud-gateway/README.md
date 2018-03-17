filterOrder:filter执行顺序, 通过数字指定 
shouldFilter:filter是否需要执行 true执行 false 不执行 
run : filter具体逻辑 
filterType :filter类型,分为以下几种

pre:请求执行之前filter 
route: 处理请求, 进行路由 
post: 请求处理完成后执行的filter 
error:出现错误时执行的filter