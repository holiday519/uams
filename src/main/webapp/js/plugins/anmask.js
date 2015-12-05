angular.module('anMask', [])
	.factory("anmaskService", function() {
		return function() {
			var init = function(elem) {
				return angular.element(elem);
			};
			var isMasked = function(elem) {
				elem = init(elem);
				return elem.hasClass("masked");
			};
			var mask = function(elem, label) {
				elem = init(elem);
				if(isMasked(elem)) {
					unmask(elem);
				}
				/*if(elem.css("position") == "static") {
					elem.addClass("masked-relative");
				}*/
				elem.addClass("masked");
				var maskDiv = '<div class="loadmask mt0"></div>';
				elem.append(maskDiv);
			};
			var unmask = function(elem) {
				elem = init(elem);
				elem.find(".loadmask-msg,.loadmask").remove();
				elem.removeClass("masked");
				elem.removeClass("masked-relative");
				elem.find("select").removeClass("masked-hidden");
			};
			return {
				mask : mask,
				unmask : unmask
			};
		}();
	});