package com.android.data_api.utils

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class FakeInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) =
        Response.Builder()
            .request(chain.request())
            .code(SUCCESS_CODE)
            .protocol(Protocol.HTTP_2)
            .message("fake")
            .body(testJson.toResponseBody(mediaType.toMediaType()))
            .addHeader("content-type", "application/json")
            .build()

    companion object {

        private const val ERROR_CODE = 404
        private const val SUCCESS_CODE = 200

        private const val mediaType = "application/json"
        private const val testJson: String = "[\n" +
            "  {\n" +
            "    \"id\": \"1\",\n" +
            "    \"name\": \"Ekko True Damage\",\n" +
            "    \"description\": \"Rompe la Grieta con ritmo, con estilo y flow. Destruye la base enemiga con tus rimas y versos callejeros\",\n" +
            "    \"company\": \"True Damage\",\n" +
            "    \"limit\": 2,\n" +
            "    \"exchangeLimit\": 1,\n" +
            "    \"productCode\": 265,\n" +
            "    \"endDate\": 1605718549000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": false,\n" +
            "    \"image\": \"https://i.pinimg.com/originals/69/6e/48/696e48f784cd0c3604f11fe5539b3c35.png\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 1,\n" +
            "      \"description\": \"20%\"\n" +
            "    },\n" +
            "    \"relatedProducts\": [\n" +
            "      {\n" +
            "        \"productName\": \"Yasuo True Damage\",\n" +
            "        \"image\": \"https://64.media.tumblr.com/ba7562b32cf7e18eb6579572e330ec07/d1c69571b22352b5-47/s400x600/d3ae0cf6520b881dd6b4dcb8bac00809a33d622e.png\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Qiyana True Damage\",\n" +
            "        \"image\": \"https://pm1.narvii.com/7373/2fb2a0f34d92004fe91a50b1d96ce787fef1014dr1-1080-944v2_128.jpg\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Akali True Damage\",\n" +
            "        \"image\": \"https://ih1.redbubble.net/image.972632260.9180/flat,128x128,075,t-pad,128x128,f8f8f8.u1.jpg\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Senna True Damage\",\n" +
            "        \"image\": \"https://ih1.redbubble.net/image.958904891.0345/flat,128x128,075,t-pad,128x128,f8f8f8.jpg\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"2\",\n" +
            "    \"name\": \"Kindred Spirit Blossom\",\n" +
            "    \"description\": \"La Cabra y el Lobo están preparados para la caza. Adquiere esta nueva skin y escala a diamante sin piedad\",\n" +
            "    \"company\": \"Spirit Blossom\",\n" +
            "    \"limit\": 6,\n" +
            "    \"exchangeLimit\": 4,\n" +
            "    \"productCode\": 2564,\n" +
            "    \"endDate\": 1606841749000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": true,\n" +
            "    \"image\": \"https://cdn.oneesports.gg/wp-content/uploads/2020/07/LoL_SpiritBlossomKindred-1024x603.jpg\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 2,\n" +
            "      \"description\": \"Descuento 2da unidad\"\n" +
            "    },\n" +
            "    \"relatedProducts\": [\n" +
            "      {\n" +
            "        \"productName\": \"Ahri Spirit Blossom\",\n" +
            "        \"image\": \"https://64.media.tumblr.com/6ff491484fbd25432cd83757ea179e65/90c5e11d362279f0-b6/s400x600/0d429186a5b4f6e8727ac6b2b0a014f995b9b542.png\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Teemo Spirit Blossom\",\n" +
            "        \"image\": \"https://64.media.tumblr.com/ef4094693997bb62146aa595f33e32e1/90c5e11d362279f0-bb/s400x600/6f98b4bf201f78463b7368ea232856faeeea3ae1.png\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Yone Spirit Blossom\",\n" +
            "        \"image\": \"https://cdn130.picsart.com/333327704069201.jpg?type=webp&to=crop&r=256\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Vayne Spirit Blossom\",\n" +
            "        \"image\": \"https://lolskin.weblog.vc/img/wallpaper/tiles/Vayne_14.jpg?10.15.2\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Thresh Spirit Blossom\",\n" +
            "        \"image\": \"https://64.media.tumblr.com/59e7da3ddadf0e511880b05c3fcfb5a4/0dfbf548c6e04605-4d/s400x600/4ac39328e62ac1a39b019ee19297a46adae5d29a.png\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"productName\": \"Cassiopeia Spirit Blossom\",\n" +
            "        \"image\": \"https://steamuserimages-a.akamaihd.net/ugc/1463058539669881101/8E483353B2D7070573D333EB72D097EFAC6651F6/?imw=637&imh=358&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=true\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"3\",\n" +
            "    \"name\": \"Ezreal Battle Academy\",\n" +
            "    \"description\": \"¿Te gusta el anime? ¿Te gusta My Hero Academy? Pues no se diga más! Esta es la skin perfecta para enseñarle a tus amigos que tu te dejas el dinerito en el LOL. La skin perfecta para pedirla como regalo de Navidad\",\n" +
            "    \"company\": \"Battle Academy\",\n" +
            "    \"limit\": 1,\n" +
            "    \"exchangeLimit\": 1,\n" +
            "    \"productCode\": 45251,\n" +
            "    \"endDate\": 1608828949000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": false,\n" +
            "    \"image\": \"https://i.pinimg.com/originals/42/c3/96/42c396a35d5513f331513a064159c6a5.png\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 1,\n" +
            "      \"description\": \"24%\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"4\",\n" +
            "    \"name\": \"Sona PsyOps\",\n" +
            "    \"description\": \"No diremos nada de esta skin, no queremos sufrir todo el poder que desprende. Un must!\",\n" +
            "    \"company\": \"PsyOps\",\n" +
            "    \"limit\": 10,\n" +
            "    \"exchangeLimit\": 5,\n" +
            "    \"productCode\": 45251,\n" +
            "    \"endDate\": 1606236949000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": true,\n" +
            "    \"image\": \"https://static1-es.millenium.gg/articles/4/25/41/4/@/117868-5-article_m-2.jpg\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 1,\n" +
            "      \"description\": \"22%\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"5\",\n" +
            "    \"name\": \"Jinx Odyssey\",\n" +
            "    \"description\": \"Vuélvete Loc@ e imparable destrozando la Grieta con tus metralletas y lanza cohetes. Advertencia: No nos hacemos responsables si destrozas tu casa después de que te roben un Pentakill\",\n" +
            "    \"company\": \"Odyssey\",\n" +
            "    \"limit\": 6,\n" +
            "    \"exchangeLimit\": 6,\n" +
            "    \"productCode\": 666,\n" +
            "    \"endDate\": 1607273749000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": false,\n" +
            "    \"image\": \"https://1.bp.blogspot.com/-g2kCbL3xWF8/X2xNUgdgRKI/AAAAAAAADAU/pDoS5e2RaUYaxEB87-fkpEgcaJR3IhrIQCNcBGAsYHQ/w914-h514-p-k-no-nu/lol-jinx-odyssey-uhdpaper.com-4K-5.2833-wp.thumbnail.jpg\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 2,\n" +
            "      \"description\": \"30%\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"6\",\n" +
            "    \"name\": \"Kog'Maw Arcanist\",\n" +
            "    \"description\": \"¿Tus padres o tu pareja no te dejan tener una mascota? Pues no pasa nada!! Compra esta skin y siente la diversión y ternura de llevar a esta criaturita de un lado al otro. Míralo, incluso va por la pelota! #cute #pet\",\n" +
            "    \"company\": \"Arcanist\",\n" +
            "    \"limit\": 46,\n" +
            "    \"exchangeLimit\": 2,\n" +
            "    \"productCode\": 25616,\n" +
            "    \"endDate\": 1609433749000,\n" +
            "    \"unlockDate\": 0,\n" +
            "    \"activated\": false,\n" +
            "    \"image\": \"https://static1-www.millenium.gg/articles/0/18/93/0/@/189992-96019-article_m-1.jpg\",\n" +
            "    \"discount\": {\n" +
            "      \"type\": 2,\n" +
            "      \"description\": \"Unidad gratis\"\n" +
            "    }\n" +
            "  }\n" +
            "]"
    }
}