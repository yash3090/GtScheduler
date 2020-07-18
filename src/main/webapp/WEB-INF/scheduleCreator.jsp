<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 
    
 
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>

<meta charset="UTF-8">
<title>Schedule Creator</title>
</head>




<body style="background-color:black;">




<style>

.header img {
  float: right;
  width: 150px;
  height: 150px;
  background: #555;
}

.header h1 {
  position: relative;
  top: 18px;
  left: 10px;
}

</style>


<div class="header">
  <img src="/images/buzzlogo.png" alt="logo" />
 <h1 style="color:gold"> <b><center>Georgia Tech Schedule Creator</center></b></h1>
</div>









<br>
<br>

<center>
<p style="color:white"> The algorithm scrapes data from RateMyProfessor and CourseCritque to create the <b><u>best</u></b> schedule for you, automatically syncing lectures with studios/labs <br> 
<br><font style="font-size:10px">Click on rightmost column for details on what to enter in each text field</font>
<br>

</p>
 </center>
<center>

<font color="red" style="font-size:25px">${errorMessage}</font>
  </center>
  
  
  <style>
a:link {
  color: white;
  background-color: transparent;
  text-decoration: none;
}

a:visited {
  color: white;
  background-color: transparent;
  text-decoration: none;
}

a:hover {
  color: LightGreen ;
  background-color: transparent;
  text-decoration: underline;
}

a:active {
  color: white;
  background-color: transparent;
  text-decoration: underline;
}
</style>
  
  <style>
/* Popup container - can be anything you want */
.popup {
  position: relative;
  display: inline-block;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

/* The actual popup */
.popup .popuptext {
  visibility: hidden;
  width: 250px;
  background-color: #555;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 8px 0;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 100%;
  margin-left: -80px;
}

/* Popup arrow */
.popup .popuptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 10%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

/* Toggle this class - hide and show the popup */
.popup .show {
  visibility: visible;
  -webkit-animation: fadeIn 1s;
  animation: fadeIn 1s;
}

/* Add animation (fade in the popup) */
@-webkit-keyframes fadeIn {
  from {opacity: 0;} 
  to {opacity: 1;}
}

@keyframes fadeIn {
  from {opacity: 0;}
  to {opacity:1 ;}
}
</style>




<script>
// When the user clicks on div, open the popup
function myFunction() {
  var popup = document.getElementById("myPopup");
  popup.classList.toggle("show");
}
</script>


<script>
// When the user clicks on div, open the popup
function myFunctionTwo() {
  var popup = document.getElementById("myPopupTwo");
  popup.classList.toggle("show");
}
</script>



<script>
// When the user clicks on div, open the popup
function myFunctionThree() {
  var popup = document.getElementById("myPopupThree");
  popup.classList.toggle("show");
}
</script>

<script>
// When the user clicks on div, open the popup
function myFunctionFour() {
  var popup = document.getElementById("myPopupFour");
  popup.classList.toggle("show");
}
</script>


<script>
// When the user clicks on div, open the popup
function myFunctionFive() {
  var popup = document.getElementById("myPopupFive");
  popup.classList.toggle("show");
}
</script>



<script>
// When the user clicks on div, open the popup
function myFunctionSix() {
  var popup = document.getElementById("myPopupSix");
  popup.classList.toggle("show");
}
</script>

  
  <center>
    <form method="post">
    <table class="center">
    <tr width="100%">
        <td><p style="color:gold"> Courses :</p></td> 
        <td style="color:white"><input type="text" name="courses" />  <div class="popup" onclick="myFunction()"> * Mandatory Field
  <span class="popuptext" id="myPopup">Enter with only ' , ' separating courses eg: MATH 1554,CS 1332,ECE 2035</span>
</div>
  </td>
        </tr>
    
   
     
    
      
        <tr width="100%">
        <td><p style="color:gold"> Start time :</p> </td>
        <td style="color:white"> <input type="text" name="startTime" /> <div class="popup" onclick="myFunctionTwo()"> * Optional Field
  <span class="popuptext" id="myPopupTwo">Time should be in 2400 format, eg: 1200</span>
</div> </td>
        </tr>
        
        
      
      
        
        <tr width="100%">
        <td><p style="color:gold"> End time : </p></td>
        <td style="color:white"> <input type ="text" name = "endTime"/> <div class="popup" onclick="myFunctionThree()"> * Optional Field
  <span class="popuptext" id="myPopupThree">Time should be in 2400 format, eg: 0900</span>
</div> </td>
        </tr>
        
       
      
        
        <tr width="100%">
        <td><p style="color:gold"> Wanted CRN : </p></td>
        <td style="color:white"> <input type="text" name = "wantedCRN"/> <div class="popup" onclick="myFunctionFive()"> * Optional Field
  <span class="popuptext" id="myPopupFive">If you want specific sections enter the CRN's without any spaces eg: 89989,99999,0000. Note: CRN must be of a course entered above and only one wanted CRN per course</span>
</div> </td>
        </tr>
        
        
    
        
        <tr width="100%">
        <td> <p style="color:gold"> Unwanted CRN : </p></td>
        <td style="color:white"><input type="text" name = "unwantedCRN" /> <div class="popup" onclick="myFunctionFour()"> * Optional Field
  <span class="popuptext" id="myPopupFour">If you dont want specific sections enter the CRNs.
Note: There can be multiple CRNs for one course, this field can be used for removing sections which take place at a different campus</span>
</div> </td>
        </tr>
     
        <br>
        
        
        <tr width="100%">
        <td> <p style="color:gold"> Year :</p> </td>
        <td style="color:white"> <input type="text" name = "year" /> 
        
         <div class="popup" onclick="myFunctionSix()"> * Mandatory Field
  <span class="popuptext" id="myPopupSix">eg: 2020</span>
</div> </td>
        </tr>
        </Table>
        
        <br>
        <Table>
        <tr>
                    <td width="20%"><p style="color:gold"> Term : </p></td>
                    
                    <td width="30%" style="color:gold"><input type="radio" name="Term" 
                        value="05"> Summer </td>
                    <td width="20%" style="color:gold"><input type="radio" name="Term" value="08" 
					    checked> Fall</td>
                    <td width="30%" style="color:gold"><input type="radio" name="Term" value="02">
					   Spring</td>
                </tr>
                </Table>
                <br>

         <br>
         <br>
        <input type="submit" />
 
    </form>
    
  </center>
<script>
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();   
});
</script>






<body>
      <marquee style="color:SkyBlue">For feedback/suggestions email at: gtschedulecreator@gmail.com</marquee>
   </body>
</body>
</html>