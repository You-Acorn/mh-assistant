//When the first span has a mouse over it the background images changes to a monster hunter image
document.getElementsByTagName("span").item(0).onmouseenter = 
	function() {
		document.body.style.backgroundImage = "url('https://images4.alphacoders.com/110/thumb-1920-1102315.jpg')";
		document.body.style.backgroundRepeat = "no-repeat";
		document.body.style.backgroundSize = "cover";
	}
	
//When the first span has a mouse leave it the background image is removed
document.getElementsByTagName("span").item(0).onmouseleave = function() {
	document.body.style.backgroundImage = "";
}