default_toolbar_buttons_array = ["fraction",
    "square_root",
    "cube_root",
    "root",
    "square",
    "cube",
    "power",
    "log",
    "log_n",
    "ln",
    'pi',
    'sin',
    'cos',
    'tan',
    'cot',
    'sec',
    'cosec',
    'not_equal',
    'greater_equal',
    'less_equal',
    'greater_than',
    'less_than',
    'plus_minus'
];
	
toolbar_button_latex_definitions = {
	"fraction": {
		latex: "\\frac{}{}",
		moveto: "Up",
		movefor: 1,
		tab: 1,
		icon:'\\frac{\\square}{\\square}'
	},
	"square_root": {
		latex: "\\sqrt{}",
		moveto: "Left",
		movefor: 1,
		tab: 1,
		icon:'\\sqrt{\\square}'
	},
	"cube_root": {
	    latex: "\\sqrt[3]{}",
	    moveto: "Left",
	    movefor: 1,
	    tab: 1,
	    icon:'\\sqrt[3]{\\square}'
	},
	"root": {
	    latex: "\\sqrt[{}]{}",
	    moveto: "Left",
	    movefor: 2,
	    tab: 1,
	    icon:'\\sqrt[\\square]{\\square}'
	},
	"square": {
	    latex: "\\^2",
	    moveto: "Right",
	    movefor: 0,
	    tab: 1,
	    icon:'\\square^2'
	},
	"cube": {
	    latex: "\\^3",
	    moveto: "Right",
	    movefor: 0,
	    tab: 1,
	    icon:'\\square^3'
	},
	"power": {
	    latex: "\\^{}",
	    moveto: "Up",
	    movefor: 1,
	    tab: 1,
	    icon:'\\square^\\square'
	},
    "log": {
	    latex: "\\log",
	    moveto: "Right",
	    movefor: 0,
	    tab: 1,
	    icon:'\\log(\\square)'
	},
    "ln": {
	    latex: "\\ln",
	    moveto: "Right",
	    movefor: 0,
	    tab: 1,
	    icon:'\\ln(\\square)'
	},
    "log_n": {
	    latex: "\\log_{}",
	    moveto: "Left",
	    movefor: 2,
	    tab: 1,
	    icon:'\\log_{\\square}{\\square}'
	},
	"multiplication": {
	    latex: "\\times",
	    tab: 2,
	    icon:'\\times'
	},
	"division": {
	    latex: "\\div",
	    tab: 2,
	    icon:'\\div'
	},
	"plus": {
	    latex: "+",
	    tab: 2,
	    icon:'\\+'
	},
	"minus": {
	    latex: "-",
	    tab: 2,
	    icon:'\\-'
	},
	"plus_minus": {
	    latex: "\\pm",
	    tab: 2,
	    icon:'\\pm'
	},
	"pi": {
	    latex: "\\pi",
	    tab: 2,
	    icon:'\\pi'
	},
	"sin": {
    	latex: "\\sin",
    	moveto: "Left",
        movefor: 0,
    	tab: 3,
    	icon:'\\sin(\\square)'
    },
    "cos": {
        latex: "\\cos",
        moveto: "Left",
        movefor: 0,
        tab: 3,
        icon:'\\cos(\\square)'
    },
    "tan": {
        latex: "\\tan",
        moveto: "Left",
        movefor: 0,
        tab: 3,
        icon:'\\tan(\\square)'
    },
    "cot": {
        latex: "\\cot",
        moveto: "Left",
        movefor: 0,
        tab: 3,
        icon:'\\cot(\\square)'
    },
    "sec": {
        latex: "\\sec",
        moveto: "Left",
        movefor: 0,
        tab: 3,
        icon:'\\sec(\\square)'
    },
    "cosec": {
        latex: "\\cosec",
        moveto: "Left",
        movefor: 0,
        tab: 3,
        icon:'\\cosec(\\square)'
    },
	"not_equal": {
	    latex: "\\neq",
	    tab: 2,
	    icon:'\\neq'
	},
	"equal": {
	    latex: "=",
	    tab: 2,
	    icon:'='
	},
	"greater_equal": {
	    latex: "\\geq",
	    tab: 2,
	    icon:'\\geq'
	},
	"less_equal": {
	    latex: "\\leq",
	    tab: 2,
	    icon:'\\leq'
	},
	"greater_than": {
	    latex: "\\gt",
	    tab: 2,
	    icon:'\\gt'
	},
	"less_than": {
	    latex: "\\lt",
	    tab: 2,
	    icon:'\\lt'
	},
	"integral_nolimit": {
	    latex: "\\int{}\\mathrm{d}{}",
	    moveto: "Left",
        movefor: 3,
	    tab: 2,
	    icon:'\\int{\\square}\\mathrm{d}{\\square}'
	},
	"round_brackets": {
	    latex: "\\left(\\right)",
	    moveto: "Left",
	    movefor: 1,
	    tab: 1,
	    icon:'\\left(\\square\\right)'
	},
	"x": {
    	latex: "x",
    	tab: 2,
        icon:'x'
    },
    "y": {
        latex: "y",
        tab: 2,
        icon:'y'
    },
    "m": {
    	latex: "m",
    	tab: 2,
        icon:'m'
    },
    "n": {
        latex: "n",
        tab: 2,
        icon:'n'
    },
    "1": {
    	latex: "1",
    	tab: 2,
        icon:'1'
    },
    "2": {
        latex: "2",
        tab: 2,
        icon:'2'
    },
    "3": {
    	latex: "3",
    	tab: 2,
        icon:'3'
    },
    "4": {
        latex: "4",
        tab: 2,
        icon:'4'
    },
    "5": {
    	latex: "5",
    	tab: 2,
        icon:'5'
    },
    "6": {
        latex: "6",
        tab: 2,
        icon:'6'
    },
    "7": {
    	latex: "7",
    	tab: 2,
        icon:'7'
    },
    "8": {
        latex: "8",
        tab: 2,
        icon:'8'
    },
    "9": {
    	latex: "9",
    	tab: 2,
        icon:'9'
    },
    "0": {
        latex: "0",
        tab: 2,
        icon:'0'
    }
};

keyboard_buttons = {
    'letters': [{
        'value': 'q',
        'type': 'write',
        'class': 'ks',
        'display': 'q',
        'new_line': false
    }, {
        'value': 'w',
        'type': 'write',
        'class': 'ks',
        'display': 'w',
        'new_line': false
    }, {
        'value': 'e',
        'type': 'write',
        'class': 'ks',
        'display': 'e',
        'new_line': false
    }, {
        'value': 'r',
        'type': 'write',
        'class': 'ks',
        'display': 'r',
        'new_line': false
    }, {
        'value': 't',
        'type': 'write',
        'class': 'ks',
        'display': 't',
        'new_line': false
    }, {
        'value': 'y',
        'type': 'write',
        'class': 'ks',
        'display': 'y',
        'new_line': false
    }, {
        'value': 'u',
        'type': 'write',
        'class': 'ks',
        'display': 'u',
        'new_line': false
    }, {
        'value': 'i',
        'type': 'write',
        'class': 'ks',
        'display': 'i',
        'new_line': false
    }, {
        'value': 'o',
        'type': 'write',
        'class': 'ks',
        'display': 'o',
        'new_line': false
    }, {
        'value': 'p',
        'type': 'write',
        'class': 'ks',
        'display': 'p',
        'new_line': true
    }, {
        'value': 'a',
        'type': 'write',
        'class': 'ks',
        'display': 'a',
        'new_line': false
    }, {
        'value': 's',
        'type': 'write',
        'class': 'ks',
        'display': 's',
        'new_line': false
    }, {
        'value': 'd',
        'type': 'write',
        'class': 'ks',
        'display': 'd',
        'new_line': false
    }, {
        'value': 'f',
        'type': 'write',
        'class': 'ks',
        'display': 'f',
        'new_line': false
    }, {
        'value': 'g',
        'type': 'write',
        'class': 'ks',
        'display': 'g',
        'new_line': false
    }, {
        'value': 'h',
        'type': 'write',
        'class': 'ks',
        'display': 'h',
        'new_line': false
    }, {
        'value': 'j',
        'type': 'write',
        'class': 'ks',
        'display': 'j',
        'new_line': false
    }, {
        'value': 'k',
        'type': 'write',
        'class': 'ks',
        'display': 'k',
        'new_line': false
    }, {
        'value': 'l',
        'type': 'write',
        'class': 'ks',
        'display': 'l',
        'new_line': true
    }, {
        'value': 'CapsLock',
        'type': 'custom',
        'class': 'ks long icon',
        'display': '&#8673;',
        'new_line': false
    }, {
        'value': 'z',
        'type': 'write',
        'class': 'ks',
        'display': 'z',
        'new_line': false
    }, {
        'value': 'x',
        'type': 'write',
        'class': 'ks',
        'display': 'x',
        'new_line': false
    }, {
        'value': 'c',
        'type': 'write',
        'class': 'ks',
        'display': 'c',
        'new_line': false
    }, {
        'value': 'v',
        'type': 'write',
        'class': 'ks',
        'display': 'v',
        'new_line': false
    }, {
        'value': 'b',
        'type': 'write',
        'class': 'ks',
        'display': 'b',
        'new_line': false
    }, {
        'value': 'n',
        'type': 'write',
        'class': 'ks',
        'display': 'n',
        'new_line': false
    }, {
        'value': 'm',
        'type': 'write',
        'class': 'ks',
        'display': 'm',
        'new_line': false
    }, {
        'value': 'Backspace',
        'type': 'keystroke',
        'class': 'ks long icon',
        'display': '&#8678;',
        'new_line': true
    }, {
        'value': 'numpad',
        'type': 'custom',
        'class': 'ks long',
        'display': '123',
        'new_line': false
    }, {
        'value': ',',
        'type': 'write',
        'class': 'ks',
        'display': ',',
        'new_line': false
    }, {
        'value': '\\ ',
        'type': 'write',
        'class': 'ks too_long',
        'display': 'Space',
        'new_line': false
    }, {
        'value': '.',
        'type': 'write',
        'class': 'ks',
        'display': '.',
        'new_line': false
    }, {
        'value': 'numpad',
        'type': 'custom',
        'class': 'ks long',
        'display': '123',
        'new_line': false
    }],
    'numbers': [{
        'value': 'x',
        'type': 'write',
        'class': 'ks',
        'display': 'x',
        'new_line': false
	}, {
        'value': '1',
        'type': 'write',
        'class': 'ks',
        'display': '1',
        'new_line': false
    }, {
        'value': '2',
        'type': 'write',
        'class': 'ks',
        'display': '2',
        'new_line': false
    }, {
        'value': '3',
        'type': 'write',
        'class': 'ks',
        'display': '3',
        'new_line': false
    }, {
        'value': '+',
        'type': 'write',
        'class': 'ks',
        'display': '+',
        'new_line': false
    }, {
        'value': '-',
        'type': 'write',
        'class': 'ks',
        'display': '&#8315;',
        'new_line': true
    }, {
        'value': 'y',
        'type': 'write',
        'class': 'ks',
        'display': 'y',
        'new_line': false
    }, {    
		'value': '4',
        'type': 'write',
        'class': 'ks',
        'display': '4',
        'new_line': false
    }, {
        'value': '5',
        'type': 'write',
        'class': 'ks',
        'display': '5',
        'new_line': false
    }, {
        'value': '6',
        'type': 'write',
        'class': 'ks',
        'display': '6',
        'new_line': false
    }, {
        'value': '*',
        'type': 'cmd',
        'class': 'ks',
        'display': '&times;',
        'new_line': false
    }, {
	'value': '/',
        'type': 'cmd',
        'class': 'ks',
        'display': '&#247;',
        'new_line': true
    }, {
        'value': '\\pi',
        'type': 'write',
        'class': 'ks',
        'display': '&pi;',
        'new_line': false
    }, {
        'value': '7',
        'type': 'write',
        'class': 'ks',
        'display': '7',
        'new_line': false
    }, {
        'value': '8',
        'type': 'write',
        'class': 'ks',
        'display': '8',
        'new_line': false
    }, {
        'value': '9',
        'type': 'write',
        'class': 'ks',
        'display': '9',
        'new_line': false
    }, {
        'value': '(',
        'type': 'cmd',
        'class': 'ks',
        'display': '(',
        'new_line': false
    }, {
		'value': ')',
        'type': 'cmd',
        'class': 'ks',
        'display': ')',
        'new_line': false
    }, {
        'value': 'letters',
        'type': 'custom',
        'class': 'ks long1',
        'display': 'ABC',
        'new_line': false
    }, {
        'value': '0',
        'type': 'write',
        'class': 'ks',
        'display': '0',
        'new_line': false
    }, {
        'value': '.',
        'type': 'write',
        'class': 'ks',
        'display': '.',
        'new_line': false
    }, {
        'value': '=',
        'type': 'write',
        'class': 'ks',
        'display': '=',
        'new_line': false
    }, {
		'value': 'Backspace',
        'type': 'keystroke',
        'class': 'ks icon',
        'display': '&#8678;',
        'new_line': true
    }]
};