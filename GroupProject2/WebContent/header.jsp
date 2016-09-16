<div class="logoutHeader">
	<ul style="float: left">
		<li><a class="active" href="ViewAllProductsServlet">View All
				Products</a></li>
		<li><a href="ViewCart.jsp">View Cart</a></li>
		<li class="dropdown"><a href="javascript:void(0)" class="pobtn"
			onclick="showPOI()">View Previous Order</a>
			<div id="POInput" class="dropdown-content">
				<form action="ViewPreviousOrderServlet" method="post" class="ddEl">
					<label class="ddEl">Order Number:</label>
					<input type="text"
						class="ddEl" name="order_no" min="0" maxlength="6"
						pattern="[0-9]{6}" placeholder="Ex. 246810"
						title="Number's Only (six required)" required />
					 <input	type="submit" class="ddEl" value="Go" />
				</form>
			</div></li>
	</ul>
	<a href="LogOutServlet" style="float: right">Logout</a>
</div>
<script>
	//Show DropDown for search
	function showPOI() {
		document.getElementById("POInput").classList.toggle("show");
	}
	// Close the dropdown if the user clicks outside of it
	window.onclick = function(event) {
		if (!event.target.matches('.pobtn') && !event.target.matches('.ddEl')
				&& !event.target.matches('.dropdown-content')) {

			var dropdowns = document.getElementsByClassName("dropdown-content");
			var i;
			for (i = 0; i < dropdowns.length; i++) {
				var openDropdown = dropdowns[i];
				if (openDropdown.classList.contains('show')) {
					openDropdown.classList.remove('show');
				}
			}
		}
	};
</script>
