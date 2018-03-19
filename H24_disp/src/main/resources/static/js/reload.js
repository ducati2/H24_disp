$(window).resize(function () {
	echarts.init(document.getElementById('clgl-2_1')).resize();
    echarts.init(document.getElementById('clgl-2_2')).resize();
    echarts.init(document.getElementById('clgl-3_1')).resize();
    echarts.init(document.getElementById('clgl-3_2')).resize();
    echarts.init(document.getElementById('clgl-3_3')).resize();
});
var options;
var para = unescape(window.location.search);
/*<![CDATA[*/
var StartDate = para.substring(para.indexOf("=")+1,para.indexOf("&"));
var EndDate = para.substring(para.indexOf("&")+9);
/*]]>*/
function ReLoad(StartDate, EndDate) { 
	document.getElementById("StartDate").defaultValue = StartDate;
	document.getElementById("EndDate").defaultValue = EndDate;
	$.ajax({  
        url:"./getDataByConditions",
        type:"get",  
        data:{
        	"StartDate":StartDate,
        	"EndDate":EndDate
        },
        success:function(data){
            options = data;
            echarts.init(document.getElementById('clgl-2_1')).setOption(options[0]);
            echarts.init(document.getElementById('clgl-2_2')).setOption(options[1]);
            echarts.init(document.getElementById('clgl-3_1')).setOption(options[3]);
            echarts.init(document.getElementById('clgl-3_2')).setOption(options[4]);
            echarts.init(document.getElementById('clgl-3_3')).setOption(options[5]);
        },
		error:function(XMLHttpRequest,status,data){
			callback({
				errorcode:-1,
				msg:data.statusText
			});
		}
    });
}

function jump(){
	/*<![CDATA[*/
	var url = './all?StartDate='.concat(document.getElementsByTagName('input')[0].value,"&EndDate=",document.getElementsByTagName('input')[1].value);
	/*]]>*/
	location.href = url;
}