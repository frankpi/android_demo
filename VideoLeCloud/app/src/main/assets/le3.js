<script>
        uuid = "tyewufdsve";
        vuid = "1ac5c49cb8";
        function callback(t,d){
            if(typeof(gameVideo)=="undefined")return;
            if(t=="videoStart"){
                gameVideo.startPlay(uuid, vuid);
            }else if(t=="videoPause"){
                gameVideo.onVideoPause();
            }else if(t=="videoResume"){
                gameVideo.onVideoResume()
            }
        }
        var player = new CloudVodPlayer();
        var playerConf = {uu:uuid, vu:vuid,'width':'100%','height':'100%',callbackJs:"callback", fullscreen:"0", rate:"1300"};
        player.init(playerConf,"player");
    </script>