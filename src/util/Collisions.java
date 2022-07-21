package util;

public class Collisions {
	//Format: row-x-column-y.png
	public static final String[] collisions = {"115,1", "18,19","18,20","18,21","18,22","18,23","18,24","18,25","18,26","18,27","19,19","19,27","20,19","20,27",
			"21,10","21,11","21,12","21,13","21,14","21,15","21,16","21,17","21,18","21,19","21,20","21,21","21,22",
			"21,23","21,24","21,25","21,26","21,27","21,28","21,29","21,30","21,31","21,32","21,33", "21,34","21,35",
			"22,10","22,25","25,1","25,2","26,2","27,2","28,2","29,2","29,41","29,42","29,43","30,2","30,41","31,2",
			"31,41","32,2","32,16","32,17","32,18","32,19","32,20","32,21","32,22","32,23","32,24","32,25","32,26",
			"32,27","32,28","32,29","32,41","33,3","33,16","33,29","33,41","34,1","34,2","34,3","34,4","34,5","34,16",
			"34,29","34,41","35,5","35,16","35,29","35,39","36,5","36,16","26,29","36,39","37,5","37,16","37,29","38,1",
			"38,2","38,3","38,4","38,5","38,1","38,16","38,29","39,16","39,29","40,16","40,29","40,30","40,31","40,32",
			"40,33","40,34","41,16","41,34","42,16","42,34","42,9","43,10","43,11","43,12","43,13","43,14","43,15","43,16",
			"43,34","44,9","44,35","44,36","45,8","45,9","45,10","45,11","45,12","45,13","45,14","45,15","45,16","45,17",
			"45,18","45,19","45,20","45,21","45,22","45,23","45,24","45,25","45,26","45,27","45,28","45,29","45,30","45,31",
			"45,31","45,31","45,32","45,33","45,34","45,35","45,36", "115,43","115,42","115,41","115,40","115,39","115,38","115,37","115,36","115,35","115,34",
			"115,33","115,32","115,31","115,30","115,29","115,28","115,27","115,26","115,25","115,24","115,23","115,22","115,21","115,20","115,19","115,18","115,17",
			"115,16","115,15","115,14","115,15","115,14","115,13","115,12","115,11","115,10","115,9","115,8","115,7","115,6","115,5","115,4","115,3","115,2","115,1",
			"113,32","113,31","113,30","113,29","113,28","113,17","113,16","113,15","113,14","112,17","112,28","107,36","107,35","107,34","107,33","107,32","107,31",
			"107,30","107,29","107,28","107,27","107,26","107,25","107,24","107,23","107,22","107,21","107,20","107,19","107,18","107,17","107,16","107,15","107,14",
			"107,13","107,12","107,11","107,10","107,9","107,8","107,7","106,23","106,22","105,23","105,22","104,23","104,22","103,23","103,22","102,23","102,22","100,23",
			"100,22","100,14","100,13","100,12","100,11","100,10","100,9","100,8","100,7","100,6","100,5","100,4","100,3", "80,1","80,2","80,3","80,4","80,5","80,6","80,7",
			"80,8","80,9","80,10","80,11","80,12","82,30","82,31","82,32","82,33","83,11","83,12","83,13","83,14","83,30","83,31","83,32","83,33","84,11","84,12","84,13","84,14",
			"89,40","89,41","89,42","89,43","90,1","90,25","90,26","91,9","91,10","91,11","91,12","91,13","91,14","91,15","91,16","91,17","91,18","91,19","91,20","91,21","91,22",
			"91,23","91,24","91,25","91,26","91,27","91,28","91,29","91,30","91,31","91,32","91,33","91,34","91,35","115,43","115,42","115,41","115,40","115,39","115,38","115,37",
			"115,36","115,35","115,34","115,33","115,32","115,31","115,30","115,29","115,28","115,27","115,26","115,25","115,24","115,23","115,22","115,21","115,20","115,19","115,18",
			"115,17","115,16","115,15","115,14","115,15","115,14","115,13","115,12","115,11","115,10","115,9","115,8","115,7","115,6","115,5","115,4","115,3","115,2","115,1","113,32",
			"113,31","113,30","113,29","113,28","113,17","113,16","113,15","113,14","112,17","112,28","107,36","107,35","107,34","107,33","107,32","107,31","107,30","107,29","107,28",
			"107,27","107,26","107,25","107,24","107,23","107,22","107,21","107,20","107,19","107,18","107,17","107,16","107,15","107,14","107,13","107,12","107,11","107,10","107,9",
			"107,8","107,7","106,23","106,22","105,23","105,22","104,23","104,22","103,23","103,22","102,23","102,22","100,23","100,22","100,14","100,13","100,12","100,11","100,10","100,9",
			"100,8","100,7","100,6","100,5","100,4","100,3","100,2","100,1","99,43","99,42","99,41","99,40","99,39","99,38","99,37","99,36","99,35","99,34","99,33","99,32","99,31","99,30","99,23",
			"99,22","99,14","99,13","99,12","99,11","99,10","99,9","99,8","99,7","99,6","99,5","99,4","99,3","99,2","99,1","98,43","98,42","98,41","98,40","98,39","98,38","98,37","98,36","98,35","98,34",
			"98,33","98,32","98,31","98,30","98,23","98,22","97,23","97,22","96,23","96,22","95,23","95,22","94,23","94,22","93,23","93,24","93,22","93,21","92,35","92,34","92,33","92,32","92,31","92,30",
			"92,29","92,28","92,27","92,26","92,25","92,24","92,23","92,22","92,21","92,20","92,19","92,18","92,17","92,16","92,15","92,14","92,13","92,12","92,11","92,10","92,9", "52,1","52,2","52,3","52,4","52,5","52,6","52,7","52,8","52,9","52,10","52,11","52,12","52,13","52,14","52,37",
			"52,38","52,39","52,40","52,41","52,42","52,43","53,1","53,2","53,3","53,4","53,5","53,6","53,7","53,8","53,9",
			"53,10","53,11","53,12","53,13","53,14","53,15","53,37","53,38","53,39","53,40","53,41","53,42","53,43","54,1",
			"54,2","54,3","54,4","54,5","54,6","54,7","54,8","54,9","54,10","54,11","54,12","54,13","54,14","54,15","54,37",
			"54,38","54,39","54,40","54,41","54,42","54,43","55,1","55,2","55,3","55,4","55,5","55,6","55,7","55,8","55,9",
			"55,10","55,11","55,12","55,13","55,14","55,15","55,42","55,43","56,11","55,43","56,12","55,43","56,11","56,12",
			"56,13","56,14","56,15","56,16","56,17","56,18","56,42","56,43","57,11","57,12","57,13","57,14","57,15","57,16",
			"57,17","57,18","58,11","58,12","58,13","58,14","58,15","58,16","58,17","58,18","59,29","59,30","59,31","59,32",
			"59,33","59,34","59,35","59,36","59,37","59,38","60,29","60,30","60,31","60,32","60,33","60,34","60,35","60,36",
			"60,37","60,38","61,1","61,2","61,3","61,4","61,5","61,6","61,28","61,29","61,30","61,31","61,32","61,33","61,33",
			"61,34","61,35","61,36","61,37","61,38","61,39","62,1","62,2","62,3","62,4","62,5","62,6","65,21","65,22","65,23",
			"66,21","65,22","65,23","67,21","67,22","67,23","68,21","68,22","68,23","69,11","69,12","69,13","69,14","69,15",
			"69,16","69,17","69,18","69,19","69,20","69,21","69,22","69,23","69,24","69,25","69,26","69,27","69,28","69,29",
			"70,11","70,12","70,13","70,14","70,15","70,16","70,17","70,18","70,19","70,20","70,21","70,22","70,23","70,24",
			"70,25","70,26","70,27","70,28","70,29","71,27","71,28","71,29","71,41","71,42","71,43","72,1","72,2","72,3",
			"72,4","72,27","72,28","72,29","72,30","72,31","72,41","72,42","72,43","73,1","73,2","73,3","73,4","73,27",
			"73,28","73,29","73,30","73,42","73,43","74,1","74,2","74,3","74,4","74,42","74,43", "79,43","79,42","79,41",
			"79,40","79,39","79,38","79,37","79,36","79,35","79,34","79,33","79,12","79,11","79,10","79,9","79,8","79,7","79,6","79,5","79,4","79,3","79,2","79,1","78,43","78,42","78,41",
			"78,40","78,39","78,38","78,37","78,36","78,35","78,34","78,33","78,4","78,3","78,2","78,1","77,43","77,42","77,4","77,3","77,2","77,1","76,43","76,42","76,4","76,3","76,2",
			"76,1","75,43", "75,42","75,4","75,3","75,2","75,1"};
}
