
数组中的每个字符串都至少有一个 ' : ' 字符，且该字符第一次出现的位置不能是首位。
Multipart, FormUrlEncoded: 这两个标签起标记作用，没有值。
引用两句话解释下这两个标签的意思
application/x-www-form-urlencoded ：窗体数据被编码为名称/值对。这是标准的编码格式。
multipart/form-data ： 窗体数据被编码为一条消息，页上的每个控件对应消息中的一个部分。

@Url: 用来设置relativeUrl的。不能出现多个， 不能跟@Path标签一起用，不能在@Query标签后面出现，如果方法头部标明了relativeUrl，就不能再使用这个了。该标签后面可以跟着String, java.net.URI, Class<Android.net.Uri>三种类型的参数。
@Path : 用来给relativeUrl中的"{var}"赋值的，不能赋值null。不能在@Query标签后面出现， 不能跟@Url标签一起用，必须跟relativeUrl一起使用，它的值必须符合正则表达式"[a-zA-Z][a-zA-Z0-9_-]*"，它的值必须是relativeUrl中以"{var}"形式出现过的var。
@Query：用来表示传给服务器的key=value。它修饰的参数值可以是Iterable(或者子类), Array或者其他的什么
@QueryMap : 用来表示传给服务器的多个key=value。值只能是Map类型或者其子类, Map的内容必须包含一般类型(e.g., Map<String, String>), Map的key必须是String
@Header : 用来给请求头赋值的。值会传给okhttp3.Headers。它修饰的参数值的取值跟@Query一样。
@Field :用来表示传给服务器的key=value。只有在有@FormUrlEncoded标签时才能使用此标签。它修饰的参数值的取值跟@Query一样
@FieldMap: 用来表示传给服务器的多个key=value。只有在有@FormUrlEncoded标签时才能使用此标签。它修饰的参数值的取值跟@QueryMap一样
@Part : 标签的值是key, 标签修饰的参数值是要传递给服务器的数据。只有在有@Multipart标签时才能使用此标签。它修饰的参数值的取值跟@Query一样,使用此标签时会创建一个okhttp3.Headers以供使用
okhttp3.Headers headers = okhttp2.Headers.of("Content-Disposition", "form-data; name=\"") + part.value() + "\"",
"Content-Transfer-Encoding", part.encoding());
看下官方给的Demo:
@Multipart
@PUT("user/photo")
Call<User> updateUser(@Part("photo") RequestBody photo, @Part("description") RequestBody description);
@PartMap : 表示使用了多个@Part标签。只有在有@Multipart标签时才能使用此标签。它修饰的参数值的取值跟@QueryMap一样
@Body: 表示传递一个RequestBody过去，将会使用Converter把数据转化成RequestBody。使用此标签时，不能使用@FormUrlEncoded或者@Multipart。只能使用一次
