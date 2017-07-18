var clientdataItem = [];
var clientorganizeData = [];
var clientdepartmentData = [];
var clientpostData = [];
var clientroleData = [];
var clientuserGroup = [];
var clientuserData = [];
var authorizeMenuData = [];
var authorizeButtonData = [];
var authorizeColumnData = [];
_init=function(){
	$.ajax({
	 url: contentPath + "/getGlobalData",
	 type: "post",
	 dataType: "json",
	 async: false,//
	     success: function (data) {
	         clientdataItem = data.dict;
	         clientorganizeData = data.org;
	         clientdepartmentData = data.dept;
	         clientuserData = data.user;
	         authorizeMenuData = data.authMenu;
	         authorizeButtonData = data.authBtn;
	     },
	     error: function (XMLHttpRequest, textStatus, errorThrown) {
	         dialogMsg(errorThrown, -1);
	     }
	 });
}
//lrf 修正版本// 勿动
var tablist = {
	newTab : function(n) {
		var i = n.id, t = n.url, e = n.title, r = !0, u, f;
		if (t == undefined || $.trim(t).length == 0)
			return !1;
		$(".menuTab").each(function() {
			if ($(this).data("id") == t)
				return $(this).hasClass("active")
						|| ($(this).addClass("active").siblings(".menuTab")
								.removeClass("active"), $.learuntab
								.scrollToTab(this), $(".mainContent .POWERMS_iframe")
								.each(function() {
									if ($(this).data("id") == t)
										return $(this).show()
												.siblings(".POWERMS_iframe")
												.hide(), !1
								})), r = !1, !1
		});
		r && (u = '<a href="javascript:;" class="active menuTab" data-id="'
						+ t + '">' + e + ' <i class="fa fa-remove"><\/i><\/a>', $(".menuTab")
						.removeClass("active"), f = '<iframe class="POWERMS_iframe" id="iframe'
						+ i
						+ '" name="iframe'
						+ i
						+ '"  width="100%" height="100%" src="'
						+ t
						+ '" frameborder="0" data-id="'
						+ t
						+ '" seamless><\/iframe>', $(".mainContent")
						.find("iframe.POWERMS_iframe").hide(), $(".mainContent")
						.append(f), Loading(!0), 
						$(".mainContent iframe:visible").load(function() {
							Loading(!1)
						}), $(".menuTabs .page-tabs-content").append(u), $.learuntab
						.scrollToTab($(".menuTab.active")))
	}
};
(function(n) {
	n.learuntab = {
		requestFullScreen : function() {
			var n = document.documentElement;
			n.requestFullscreen
					? n.requestFullscreen()
					: n.mozRequestFullScreen
							? n.mozRequestFullScreen()
							: n.webkitRequestFullScreen
									&& n.webkitRequestFullScreen()
		},
		exitFullscreen : function() {
			var n = document;
			n.exitFullscreen ? n.exitFullscreen() : n.mozCancelFullScreen ? n
					.mozCancelFullScreen() : n.webkitCancelFullScreen
					&& n.webkitCancelFullScreen()
		},
		refreshTab : function() {
			var i = n(".page-tabs-content").find(".active").attr("data-id"), t = n('.POWERMS_iframe[data-id="'+ i + '"]'), r = t.attr("src");
			Loading(!0);
			t.attr("src", r).load(function() {
						Loading(!1);
					})
		},
		activeTab : function() {
			var t = n(this).data("id");
			n(this).hasClass("active")
					|| (n(".mainContent .POWERMS_iframe").each(function() {
						if (n(this).data("id") == t)
							return n(this).show().siblings(".POWERMS_iframe")
									.hide(), !1
					}), n(this).addClass("active").siblings(".menuTab")
							.removeClass("active"), n.learuntab
							.scrollToTab(this))
		},
		closeOtherTabs : function() {
			n(".page-tabs-content").children("[data-id]").find(".fa-remove")
					.parents("a").not(".active").each(function() {
						n('.POWERMS_iframe[data-id="' + n(this).data("id")
								+ '"]').remove();
						n(this).remove()
					});
			n(".page-tabs-content").css("margin-left", "0")
		},
		closeTab : function() {
			var i = n(this).parents(".menuTab").data("id"), u = n(this)
					.parents(".menuTab").width(), r, t;
			return n(this).parents(".menuTab").hasClass("active")
					? (n(this).parents(".menuTab").next(".menuTab").size()
							&& (t = n(this).parents(".menuTab")
									.next(".menuTab:eq(0)").data("id"), n(this)
									.parents(".menuTab").next(".menuTab:eq(0)")
									.addClass("active"), n(".mainContent .POWERMS_iframe")
									.each(function() {
										if (n(this).data("id") == t)
											return n(this).show()
													.siblings(".POWERMS_iframe")
													.hide(), !1
									}), r = parseInt(n(".page-tabs-content")
									.css("margin-left")), r < 0
									&& n(".page-tabs-content").animate({
												marginLeft : r + u + "px"
											}, "fast"), n(this)
									.parents(".menuTab").remove(), n(".mainContent .POWERMS_iframe")
									.each(function() {
												if (n(this).data("id") == i)
													return n(this).remove(), !1
											})), n(this).parents(".menuTab")
							.prev(".menuTab").size()
							&& (t = n(this).parents(".menuTab")
									.prev(".menuTab:last").data("id"), n(this)
									.parents(".menuTab").prev(".menuTab:last")
									.addClass("active"), n(".mainContent .POWERMS_iframe")
									.each(function() {
										if (n(this).data("id") == t)
											return n(this).show()
													.siblings(".POWERMS_iframe")
													.hide(), !1
									}), n(this).parents(".menuTab").remove(), n(".mainContent .POWERMS_iframe")
									.each(function() {
												if (n(this).data("id") == i)
													return n(this).remove(), !1
											})))
					: (n(this).parents(".menuTab").remove(), n(".mainContent .POWERMS_iframe")
							.each(function() {
										if (n(this).data("id") == i)
											return n(this).remove(), !1
									}), n.learuntab
							.scrollToTab(n(".menuTab.active"))), !1
		},
		addTab : function() {
			var i, u, f;
			n(".navbar-custom-menu>ul>li.open").removeClass("open");
			n(".sidebar-menu-overlay").remove();
			n(".sidebar-menu").find(".sidebar-menu-right").css("overflow",
					"hidden");
			n(".sidebar-menu").slideUp(300);
			i = n(this).attr("data-id");
			i != "" && top.$.cookie("currentmoduleId", i, {
						path : "/"
					});
			var t = n(this).attr("href"), e = n.trim(n(this).text().replace(/[0-9]/g,'')), r = !0;//替换数字
			return t == undefined || n.trim(t).length == 0
					? !1
					: (n(".menuTab").each(function() {
						if (n(this).data("id") == t)
							return n(this).hasClass("active")
									|| (n(this).addClass("active")
											.siblings(".menuTab")
											.removeClass("active"), n.learuntab
											.scrollToTab(this), n(".mainContent .POWERMS_iframe")
											.each(function() {
												if (n(this).data("id") == t)
													return n(this)
															.show()
															.siblings(".POWERMS_iframe")
															.hide(), !1
											})), r = !1, !1
					}), r
							&& (u = '<a href="javascript:;" class="active menuTab" data-id="'
									+ t
									+ '">'
									+ e
									+ ' <i class="fa fa-remove"><\/i><\/a>', n(".menuTab")
									.removeClass("active"), f = '<iframe class="POWERMS_iframe" id="iframe'
									+ i
									+ '" name="iframe'
									+ i
									+ '"  width="100%" height="100%" src="'
									+ t
									+ '" frameborder="0" data-id="'
									+ t
									+ '" seamless><\/iframe>', n(".mainContent")
									.find("iframe.POWERMS_iframe").hide(), n(".mainContent")
									.append(f), Loading(true), n(".mainContent iframe:visible").load(
									function() {Loading(false);
									}), n(".menuTabs .page-tabs-content")
									.append(u), n.learuntab
									.scrollToTab(n(".menuTab.active"))), !1)
		},
		scrollTabRight : function() {
			var f = Math.abs(parseInt(n(".page-tabs-content")
					.css("margin-left"))), e = n.learuntab
					.calSumWidth(n(".content-tabs").children().not(".menuTabs")), u = n(".content-tabs")
					.outerWidth(!0)
					- e, r = 0, t, i;
			if (n(".page-tabs-content").width() < u)
				return !1;
			for (t = n(".menuTab:first"), i = 0; i + n(t).outerWidth(!0) <= f;)
				i += n(t).outerWidth(!0), t = n(t).next();
			for (i = 0; i + n(t).outerWidth(!0) < u && t.length > 0;)
				i += n(t).outerWidth(!0), t = n(t).next();
			r = n.learuntab.calSumWidth(n(t).prevAll());
			r > 0 && n(".page-tabs-content").animate({
						marginLeft : 0 - r + "px"
					}, "fast")
		},
		scrollTabLeft : function() {
			var f = Math.abs(parseInt(n(".page-tabs-content")
					.css("margin-left"))), e = n.learuntab
					.calSumWidth(n(".content-tabs").children().not(".menuTabs")), r = n(".content-tabs")
					.outerWidth(!0)
					- e, u = 0, t, i;
			if (n(".page-tabs-content").width() < r)
				return !1;
			for (t = n(".menuTab:first"), i = 0; i + n(t).outerWidth(!0) <= f;)
				i += n(t).outerWidth(!0), t = n(t).next();
			if (i = 0, n.learuntab.calSumWidth(n(t).prevAll()) > r) {
				while (i + n(t).outerWidth(!0) < r && t.length > 0)
					i += n(t).outerWidth(!0), t = n(t).prev();
				u = n.learuntab.calSumWidth(n(t).prevAll())
			}
			n(".page-tabs-content").animate({
						marginLeft : 0 - u + "px"
					}, "fast")
		},
		scrollToTab : function(t) {
			var f = n.learuntab.calSumWidth(n(t).prevAll()), e = n.learuntab
					.calSumWidth(n(t).nextAll()), o = n.learuntab
					.calSumWidth(n(".content-tabs").children().not(".menuTabs")), r = n(".content-tabs")
					.outerWidth(!0)
					- o, i = 0, u;
			if (n(".page-tabs-content").outerWidth() < r)
				i = 0;
			else if (e <= r - n(t).outerWidth(!0) - n(t).next().outerWidth(!0)) {
				if (r - n(t).next().outerWidth(!0) > e)
					for (i = f, u = t; i - n(u).outerWidth() > n(".page-tabs-content")
							.outerWidth()
							- r;)
						i -= n(u).prev().outerWidth(), u = n(u).prev()
			} else
				f > r - n(t).outerWidth(!0) - n(t).prev().outerWidth(!0)
						&& (i = f - n(t).prev().outerWidth(!0));
			i > 0 && (i += 30);
			n(".page-tabs-content").animate({
						marginLeft : 0 - i + "px"
					}, "fast")
		},
		calSumWidth : function(t) {
			var i = 0;
			return n(t).each(function() {
						i += n(this).outerWidth(!0)
					}), i
		},
		init : function() {//绑定事件
			$(".menuItem").on("click", top.$.learuntab.addTab);
			n(".menuTabs").on("click", ".menuTab i", top.$.learuntab.closeTab);
			n(".menuTabs").on("click", ".menuTab", top.$.learuntab.activeTab);
			n(".tabLeft").on("click", top.$.learuntab.scrollTabLeft);
			n(".tabRight").on("click", top.$.learuntab.scrollTabRight);
			n(".tabReload").on("click", top.$.learuntab.refreshTab);
			n(".tabCloseCurrent").on("click", function() {n(".page-tabs-content").find(".active i").trigger("click")});
			n(".tabCloseAll").on("click", function() {
				n(".page-tabs-content").children("[data-id]")
					.find(".fa-remove").each(function() {
						n('.POWERMS_iframe[data-id="' + n(this).data("id")+ '"]').remove();
						n(this).parents("a").remove()
					});
				n(".page-tabs-content").children("[data-id]:first").each(
						function() {
							n('.POWERMS_iframe[data-id="' + n(this).data("id")+ '"]').show();
							n(this).addClass("active")
						});
				n(".page-tabs-content").css("margin-left", "0")
			});
			n(".tabCloseOther").on("click", n.learuntab.closeOtherTabs);
			n(".fullscreen").unbind();
			n(".fullscreen").on("click", function() {
				return n(this).attr("fullscreen")? (n(this).removeAttr("fullscreen"), n.learuntab.exitFullscreen(), !1): (n(this).attr("fullscreen", "true"), n.learuntab.requestFullScreen(), !1)
			})
		}
	};
	n.learunindex = {
		load : function() {
			n("#content-wrapper").find(".mainContent").height(n(window).height()- 158);
			n(window).resize(function() {n("#content-wrapper").find(".mainContent").height(n(window).height()- 158)})
		},
		jsonWhere : function(t, i) {
			if (i != null) {
				var r = [];
				return n(t).each(function(n, t) {
							i(t) && r.push(t)
						}), r
			}
		},
		loadMenu : function() {//加载菜单
			var t = authorizeMenuData, r = "", i;
			n.each(t, function(n) {
						var i = t[n];
						i.parentid == "0"
								&& (r += '<li><a href="#" data-id="'
										+ i.moduleid + '"><i class="fa '
										+ i.icon + '"><\/i><span>'
										+ i.fullname
										+ "<\/span><\/a><em><\/em><\/li>")
					});
			i = n(".sidebar-menu-left ul").html(r);
			i.find("li").click(function() {
				var u;
				i.find("li.active").removeClass("active");
				n(this).addClass("active");
				var e = n(this).find("a").attr("data-id"), f = n.learunindex
						.jsonWhere(t, function(n) {
									return n.parentid == e
								}), r = "";
				n.each(f, function(i) {
					var u = f[i], e = n.learunindex.jsonWhere(t, function(n) {
								return n.parentid == u.moduleid
							});
					e.length > 0
							? (r += "<li>", r += '<a href="#"><i class="fa '
									+ u.icon
									+ '"><\/i><span>'
									+ u.fullname
									+ '<\/span><i class="fa fa-angle-left pull-right"><\/i><\/a>', r += "<ul>", n
									.each(e, function(n) {
										var t = e[n];
										r += '<li><a class="menuItem" data-id="'
												+ t.moduleid
												+ '" href="'
												+ t.urladdress
												+ '"><i class="fa '
												+ t.icon
												+ '"><\/i><span>'
												+ t.fullname
												+ "<\/span><\/a><\/li>"
									}), r += "<\/ul>", r += "<\/li>")
							: r += '<li><a class="menuItem" data-id="'
									+ u.moduleid + '" href="'
									+ u.urladdress + '"><i class="fa '
									+ u.icon + '"><\/i><span>' + u.fullname
									+ "<\/span><\/a><\/li>"
				});
				u = n(".sidebar-menu-right>ul").html(r);
				u.find(">li").click(function() {
					var t = n(this);
					t.find("ul>li").length > 0
							&& (t.find("ul").is(":visible") ? (t.find("ul")
									.slideUp(500), t.find(">a")
									.removeClass("active")) : (u.find("ul")
									.slideUp(500), u.find(">li>a")
									.removeClass("active"), t.find("ul")
									.slideDown(500), t.find(">a")
									.addClass("active")))
				});
				n.learuntab.init()
			});
			i.find("li:first").trigger("click");
			n(".start_menu").click(function() {
				n(".sidebar-menu").is(":visible")
						|| (n(".sidebar-menu").find(".sidebar-menu-right").css(
								"overflow", "auto"), n(".sidebar-menu").show(), n("body")
								.prepend('<div class="sidebar-menu-overlay" style="display: block;"><\/div>'), n(".sidebar-menu-overlay")
								.click(function() {
									n(this).remove();
									n(".sidebar-menu")
											.find(".sidebar-menu-right").css(
													"overflow", "hidden");
									n(".sidebar-menu").slideUp(300)
								}))
			})
		},
		indexOut : function() {
			dialogConfirm("注：您确定要安全退出本次登录吗？", function(t) {
				if(t){
					window.location.href = contentPath + "/logout";
				}
					})
		}
	}
})(jQuery)