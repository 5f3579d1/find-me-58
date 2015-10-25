$(document).ready(function(){
    //监听摇一摇作用
    opt.listenShake();
//    初始化列表
    $.get("/posts",function(infos){
        $("#xianchang-infos").append(tmpl.getXianchangInfos(infos.content));
    });

    //修改
    $(document).on("click",".edit-info-opt",function(){
        var infoid=$(this).attr("infoid");
        $.get("../js/infoById.json?id="+infoid,function(info){
            opt.setXiachangInfo(info);
        });
    });

     //详情
    $(document).on("click",".read-info-opt",function(){
        var infoid=$(this).attr("infoid");
        $.get("../js/infoById.json?id="+infoid,function(info){
            opt.setXiachangReadInfo(info);
        });
    });


    //需求摇一摇
    $(document).on("click","#info-shake",function(){
           var infoid=$(this).attr("infoid");
           opt.infoShake(infoid);
    });

    $("#edit-save").click(function(){
        opt.saveXiachangInfo();
    });
     $("#add-save").click(function(){
        opt.addXiachangInfo();
    });
//摇一摇
  $(".newest-info").click(function(){
        opt.shake();
    });

    $(".reload").click(function(){
        window.location.reload();
    });


});

// 操作
var opt=(function(){
    var optObj={};
    optObj.listenShake=function(){
        var SHAKE_THRESHOLD = 5000;
        var last_update = 0;
        var x, y, z, last_x = 0, last_y = 0, last_z = 0;

        function deviceMotionHandler(eventData) {
            var acceleration =eventData.accelerationIncludingGravity;
            var curTime = new Date().getTime();
            if ((curTime-last_update)> 10) {
                var diffTime = curTime -last_update;
                last_update = curTime;
                x = acceleration.x;
                y = acceleration.y;
                z = acceleration.z;
                var speed = Math.abs(x +y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                    shackAction();

                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
        if (window.DeviceMotionEvent) {
            window.addEventListener('devicemotion',deviceMotionHandler,false);
        } else {
//            document.getElementById("dmEvent").innerHTML = "Not supported on your device."
            alert("你手机不支持摇一摇,但你可以点击摇一摇按钮哦！");
        }
    }

    function emptyEdit(){
        $("#edit-title").val("");
        $("#edit-detail").val("");
        $("#edit-name").val("");
        $("#edit-phone").val("");
    }
    function setEdit(info){
        $("#edit-title").val(info.title);
        $("#edit-detail").val(info.detail);
        $("#edit-name").val(info.name);
        $("#edit-phone").val(info.phone);
        $("#info-shack").attr("infoid",info.id);
        $("#edit-save").attr("infoid",info.id);

    }
      function emptyRead(){
        $("#read-title").val("");
        $("#read-detail").val("");
        $("#read-name").val("");
        $("#read-phone").val("");
    }
    function setRead(info){
        $("#read-title").val(info.title);
        $("#read-detail").val(info.detail);
        $("#read-name").val(info.name);
        $("#read-phone").val(info.phone);


    }
    function shackAction(){
        $(".reload").css("display","inline-block");
        $.get("../js/shake.json",function(info){
            $("#xianchang-infos").empty().append(tmpl.getShakeInfo(info));
            $(".xianchang-info").addClass("animated").addClass("shake");
        });
    }
    optObj.setXiachangInfo=function(info){
        emptyEdit();
        setEdit(info);
    };

    optObj.setXiachangReadInfo=function(info){
        emptyRead();
        setRead(info);
    };

    optObj.infoShake=function(infoid){
        $.get("../js/infoshake.json?infoid="+infoid,function(){
            window.location.reload();
        });
    }
    optObj.saveXiachangInfo=function(){
        var id=$(this).attr("infoid");
        var title=$("#edit-title").val();
        var detail=$("#edit-detail").val();
        var name=$("#edit-name").val();
        var phone=$("#edit-phone").val();
        var postObj={"id":id,"title":title,"detail":detail,"name":name,"phone":phone}
        $.post("../js/save.json",postObj,function(){
            window.location.reload();
        });
    };
  optObj.addXiachangInfo=function(){
        var title=$("#edit-title").val();
        var detail=$("#edit-detail").val();
        var name=$("#edit-name").val();
        var phone=$("#edit-phone").val();
        var postObj={"title":title,"detail":detail,"name":name,"phone":phone}
        $.post("../js/add.json",postObj,function(){
            window.location.reload();
        });
    };
     optObj.shake=function(){
         shackAction();
    };


    return optObj;
}());
//模板代码
var tmpl=(function(){
    var tmpObj={};

    tmpObj.getXianchangInfos=function(infosObj){
        var infosHtml="";
        for(var i=0;i< infosObj.length;i++){
            var infoi=infosObj[i];
            infosHtml+=' <div class="row">'+
            '  <div class="col-xs-1"></div>'+
            '   <div class="col-xs-10 xianchang-info">'+
            '       <span infoid="'+infoi.id+'" class="glyphicon glyphicon-edit edit-info-opt" data-toggle="modal" data-target="#edit-info-modal" style="float: right;"></span>'+
            '       <h4>'+
                infoi.title+
            '       </h4>'+
            '       <div class="info-detail">'+
                infoi.detail+
                '       </div>'+
            '       <div class="info-time text-right" >'+
            '       更新时间：'+
                infoi.updatetime+
            '       </div>'+
            '       <hr>'+
            '           <div class="text-right oprate-btns">'+
            '               <button class="btn btn-default read-info-opt" data-toggle="modal" data-target="#read-info-modal">详情</button>'+
            '           <a href="tel:'+infoi.phone+'">     <button class="btn btn-default">打电话</button></a>'+
            '           </div>'+
            '       </div>'+
            '       <div class="col-xs-1"></div>'+
            '   </div>';
        }
        return infosHtml;
    };
    tmpObj.getShakeInfo=function(infoi){
        var infoHtml=' <div class="row">'+
            '  <div class="col-xs-1"></div>'+
            '   <div class="col-xs-10 xianchang-info">'+
            '       <span infoid="'+infoi.id+'" class="glyphicon glyphicon-edit edit-info-opt" data-toggle="modal" data-target="#edit-info-modal" style="float: right;"></span>'+
            '       <h4>'+
            infoi.title+
            '       </h4>'+
            '       <div class="info-detail">'+
            infoi.detail+
            '       </div>'+
            '       <div class="info-time text-right" >'+
            '       更新时间：'+
            infoi.updatetime+
            '       </div>'+
            '       <hr>'+
            '           <div class="text-right oprate-btns">'+
            '               <button class="btn btn-default read-info-opt" data-toggle="modal" data-target="#read-info-modal">详情</button>'+
            '               <a href="tel:'+infoi.phone+'"><button class="btn btn-default">打电话</button></a>'+
            '           </div>'+
            '       </div>'+
            '       <div class="col-xs-1"></div>'+
            '   </div>';;

        return infoHtml;
    };
    return tmpObj;
}());