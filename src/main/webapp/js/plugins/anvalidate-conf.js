/**
 * anvalidate验证插件的配置文件
 * 配置自定义的验证标签
 */
var validateConfig = {
	required : {
		alertText : "该字段不能为空！"
	},
	maxlength : {
		alertText : "该字段长度过长！"
	},
	minlength : {
		alertText : "该字段长度过短！"
	},
	equals : {
		alertText : "两次输入的密码不相同！"
	},
	formatname : {
		regex : /^[\w|\u4e00-\u9fa5|-]+$/,
		alertText : "该字段格式不正确！"
	},
	password : {
		regex : /^[\w|~!@#\$%\^&\*\.]{1,32}$/,
		alertText : "该字段格式不正确！"
	},
	telephone : {
		regex : /^[0-9#\*\(\)\+\-]{7,20}$/,
		alertText : "该字段格式不正确！"
	},
	email : {
		regex : /^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_0-9a-zA-Z]*))@(([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]|[a-zA-Z0-9])[.])+([a-zA-Z0-9]{2,7})+$/,
		alertText : "该字段格式不正确！"
	},
	version : {
		regex : /^[0-9|\.]+$/,
		alertText : "该字段格式不正确！"
	}
};