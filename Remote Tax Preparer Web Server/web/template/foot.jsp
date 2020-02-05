</div>
<hr>
<div class="block text-center" id="footer">
    <i>You can also find us on:</i><br>
    <a href="https://facebook.com/MCLTaxes" target="_blank" rel="noopener noreferrer"><i class="fab fa-facebook-f"></i></a>
</div>
</div>
<script>
    function toggleNav() {
        if (document.getElementById("nav").style.display == "block") {
            document.getElementById("nav").style.transform = "scale(1, 0)";
            setTimeout(function() {
                document.getElementById("nav").style.display = "none";
            }, 500)
        } else {
            document.getElementById("nav").style.display = "block";
            document.getElementById("nav").style.transform = "scale(1, 1)";
        }
    }
</script>
</body>
</html>