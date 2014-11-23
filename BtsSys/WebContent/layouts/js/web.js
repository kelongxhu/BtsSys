   function SelectMobile(){
       function closeLayer(){
	        document.getElementById("mobile_window").style.display="none";
	        document.getElementById("sd_overlay").style.display="none";
       }
        
       function openLayer(){
            document.getElementById("mobile_window").style.display="";
            document.getElementById("mb_brand").style.display="";
            document.getElementById("model").style.display="none";
			$("#stepShow").attr("class","stepShow");
           // document.getElementById("stepShow").setAttribute("class","stepShow");
        	document.getElementById("sd_overlay").style.display="";
       }  
       
       function closeAndoridLayer(){
	        document.getElementById("ad_window").style.display="none";
	        document.getElementById("sd_overlay").style.display="none";
       }
        
       function openAndroidLayer(){
            document.getElementById("ad_window").style.display="";
        	document.getElementById("sd_overlay").style.display="";
       }  
       
       function closeIphoneLayer(){
	        document.getElementById("id_window").style.display="none";
	        document.getElementById("sd_overlay").style.display="none";
       }
        
       function openIphoneLayer(){
            document.getElementById("id_window").style.display="";
        	document.getElementById("sd_overlay").style.display="";
		    document.getElementById("mobile_window").style.display="none";
       }  
       
       function closeSymbianLayer(){
	        document.getElementById("sd_window").style.display="none";
	        document.getElementById("sd_overlay").style.display="none";
       }
        
       function openSymbianLayer(){
            document.getElementById("sd_window").style.display="";
        	document.getElementById("sd_overlay").style.display="";
       }  
       function downloadFile(f_url){
          document.getElementById("mobile_window").style.display="none";
	      document.getElementById("sd_overlay").style.display="none";
	      window.location=f_url;
       }
       
       this.downloadFile=downloadFile;
       this.openLayer = openLayer;
       this.closeLayer = closeLayer;
       this.openAndroidLayer=openAndroidLayer;
       this.closeAndoridLayer=closeAndoridLayer;
       this.openIphoneLayer = openIphoneLayer;
       this.closeIphoneLayer = closeIphoneLayer;
       this.openSymbianLayer=openSymbianLayer;
       this.closeSymbianLayer=closeSymbianLayer;
    }
    var select_mobile_window = new SelectMobile();
 