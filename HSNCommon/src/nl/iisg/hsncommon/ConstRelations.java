package nl.iisg.hsncommon;   

public class ConstRelations {
	
	public static final int [][] relCode1 = 
	{
			
	/* 01 */ 		{1, 106, 107, 108, 109, 110},
	/* 02 */		{2,113},
	/* 03 */		{3,123},
	/* 04 */		{111, 114},
	/* 05 */		{4, 124},
	/* 06 */		{116},
	/* 07 */		{5, 125},
	/* 08 */		{6, 126}, 
	/* 09 */		{8},
	/* 10 */		{9},
	/* 11 */		{10},
	/* 12 */		{11},
	/* 13 */		{12, 112, 121, 128}, 
	/* 14 */		{13},
	/* 15 */		{14},
	/* 16 */		{15},
	/* 17 */		{16},
	/* 18 */		{17},
	/* 19 */		{18},
	/* 20 */		{20},
	/* 21 */		{21},
	/* 22 */		{22,  118, 122, 129}, 
	/* 23 */		{23},
	/* 24 */		{24},
	/* 25 */		{25},
	/* 26 */		{26},
	/* 27 */		{27},
	/* 28 */		{28},
	/* 29 */		{30},
	/* 30 */		{40},
	/* 31 */		{31},
	/* 32 */		{32},
	/* 33 */		{51},
	/* 34 */		{52},
	/* 35 */		{53},
	/* 36 */        {115},
	/* 37 */		{54},
	/* 38 */		{60},
	/* 39 */		{61},
	/* 40 */		{62},
	/* 41 */		{63, 117},
	/* 42 */		{64},
	/* 43 */		{65},
	/* 44 */		{66},
	/* 45 */		{67},
	/* 46 */		{68},
	/* 47 */		{69},
	/* 48 */		{70},
	/* 49 */		{71},
	/* 50 */		{72},
	/* 51 */		{73},
	/* 52 */		{74},
	/* 53 */		{75},
	/* 54 */		{76},
	/* 55 */		{77},
	/* 56 */		{78},
	/* 57 */		{79},
	/* 58 */		{81},
	/* 59 */		{82},
	/* 60 */		{83},
	/* 61 */		{84},
	/* 62 */		{85},
	/* 63 */		{86},
	/* 64 */		{87},
	/* 65 */		{88}
	};
			
			
	public static final int [][] relCode2 = {
	
	/* 01 */		{1,106, 107, 108, 109, 110},
	/* 02 */		{2,113},
	/* 03 */		{3,111, 114},
	/* 04 */		{4,116},
	/* 05 */		{5,125},
	/* 06 */		{6,126},
    /* 07 */		{8,123},
    /* 08 */		{9,124},
    /* 09 */		{12, 112, 121, 128},
    /* 10 */		{22, 118, 122, 129},
    /* 11 */		{30},
    /* 12 */		{40},
    /* 13 */		{53, 83, 115},
    /* 14 */		{54, 84},
    /* 15 */		{63, 117},
    /* 16 */		{73},
    /* 17 */		{11, 21},
    /* 18 */		{61, 71},
    /* 19 */		{81, 82},
    /* 20 */		{85, 86},
    /* 21 */		{87, 88},
    /* 22 */		{14, 16, 24, 26},
    /* 23 */		{15, 25},
    /* 24 */		{119}, 
    /* 25 */		{120}, 
    /* 26 */		{10, 20}, 
    /* 27 */		{13, 23}   // new
	};
	

	
	public static final int [] maleToFemale = new int[150];
	public static final int [] femaleToMale = new int[150];
			
	
	
	public static final int [][][] transform = 
	
	{
			/* 01 */
		
			{
				{}, {147}, {11, 21}, {11,21}, {143, 144}, {143, 144}, {81, 82}, {81, 82},
				{12, 22}, {12, 22}, {10, 20}, {10, 20}, {61, 71}, {61, 71}, {63, 73}, {63, 73}, 
				{3, 4}, {53, 54}, {8, 9}, {85, 86}, {87, 88}, {13, 23}, {15, 25}, 
				{}, {}, {30, 40}, {16, 26}
			},
			
			/* 02 */
 			{
				{2, 113}, {}, {21, 82, 149}, {21, 82, 149}, {21, 82, 149}, {21, 82, 144}, {21, 82, 149}, {21, 82, 149},
				{73}, {22, 73}, {20}, {20}, {71}, {71}, {22, 73}, {22, 73},
				{54}, {4}, {54}, {73}, {73}, {23}, {25},
				{131}, {132}, {31, 39}, {16, 26}
			}, 
	
	
			/* 03 */
			{
				{3, 123},  {3, 123}, {12, 128}, {12, 128}, {12, 125}, {12, 125}, {12, 128}, {12, 128},
				{16}, {16}, {13, 11}, {13, 11}, {63}, {63, 145}, {16}, {16},
				{30}, {30}, {30}, {16}, {16}, {15}, {131}, 
				{131}, {132}, {51}, {17}
			}, 
	
			/* 04 */
			{
				{111, 114}, {111, 114, 123}, {12, 128},  {12, 128}, {12, 125}, {12, 125}, {12, 128}, {12, 128},
				{16}, {16}, {13, 11}, {13, 11}, {63}, {63, 145}, {16}, {16},
				{3}, {53}, {83}, {85}, {87}, {16}, {15}, 
				{131}, {132}, {51}, {27}
			}, 
			
			/* 05 */
			{
				{4, 124}, {4, 124}, {22, 129}, {22, 129}, {22, 126}, {22, 126}, {22, 129}, {22, 129},
				{26}, {26}, {23, 21}, {23, 21}, {73, 146}, {73}, {26}, {26}, 
				{40}, {40}, {40}, {26}, {26}, {25}, {131}, 
				{131}, {132}, {52}, {17}
			},
	
			/* 06 */
			{
				{116}, {116, 124}, {22, 129}, {22, 129}, {22, 126}, {22, 126}, {22, 129}, {22, 129},
				{26}, {26}, {23, 21}, {23, 21}, {73, 146}, {73}, {26}, {26}, 
				{4}, {54}, {84}, {86}, {88}, {26}, {25}, 
				{131}, {132}, {52}, {27}
			}, 
			
			/* 07 */
			{
				{5, 125}, {5}, {125}, {125}, {12, 125}, {12, 125}, {12, 125}, {12, 125},
				{125}, {125}, {13, 11}, {13, 11}, {125}, {125}, {125}, {125}, 
				{30}, {30}, {30}, {16}, {16}, {15}, {131}, 
				{131}, {132}, {51}, {17}
			}, 
	
			/* 08 */
			{
				{6, 126}, {6}, {126}, {126}, {22, 126}, {22, 126}, {22, 126}, {22, 126}, 
				{126}, {126}, {23, 21}, {23, 21}, {126}, {126}, {126}, {126}, 
				{40}, {40}, {40}, {26}, {26}, {25}, {131}, 
				{131}, {132}, {52}, {27}
			}, 
			
			/* 09 */
			{
				{8}, {3, 123}, {12, 128}, {12, 128}, {12, 125}, {12, 125}, {12, 128}, {12, 128},
				{16}, {16}, {13, 11}, {13, 11}, {63}, {63}, {16}, {16}, 
				{30}, {30}, {30}, {16}, {16}, {15}, {131}, 
				{131}, {132}, {51}, {}
			}, 
			
			/* 10 */
			{
				{9}, {4, 124}, {22, 129}, {22, 129}, {22, 126}, {22, 126}, {22, 129}, {22, 129},
				{26}, {26}, {23, 21}, {23, 21}, {73}, {73}, {26}, {26}, 
				{40}, {40}, {40}, {26}, {26}, {25}, {131}, 
				{131}, {132}, {52}, {}
			},
	
			/* 11 */
			{
				{10}, {60}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {12}
			}, 
			
			/* 12 */
			{
				{11}, {61}, {10}, {10}, {}, {}, {}, {}, 
				{11, 81, 148}, {11, 81, 148}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {15}
			},
			
			/* 13 */
			{
				{12, 121, 128}, {63}, {13}, {13}, {}, {}, {}, {},
				{12, 128}, {12, 128}, {}, {}, {}, {}, {}, {}, 
				{123}, {131}, {123}, {128}, {128}, {13}, {15}, 
				{131}, {132}, {30}, {}
			}, 
			
			/* 14 */
			{
				{13}, {68}, {18}, {18}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {3}
			},
			
			/* 15 */
			{			
				{14}, {64}, {15}, {15}, {}, {}, {}, {}, 
				{3, 14}, {3, 14}, {131}, {131}, {131}, {131}, {64}, {64}, 
				{17}, {17}, {67}, {14}, {14}, {12, 14}, {12, 14}, 
				{131}, {132}, {30, 51}, {3}
			}, 
			
			/* 16 */
			{
				{15}, {65}, {131}, {131}, {}, {}, {}, {}, 
				{3, 15}, {3, 15}, {131}, {131}, {131}, {131}, {65}, {65}, 
				{17}, {17}, {67}, {15}, {15}, {12, 14}, {12, 14}, 
				{131}, {132}, {30}, {30}
			}, 
			
			/* 17 */
			{
				{16}, {66}, {15}, {15}, {}, {}, {}, {}, 
				{3, 16}, {3, 16}, {131}, {131}, {131}, {131}, {66}, {66}, 
				{17}, {17}, {67}, {16}, {16}, {12, 14}, {12, 14}, 
				{131}, {132}, {51}, {}
			}, 
			
			/* 18 */
			{
				{17}, {67}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
				
				
			/* 19 */
			{
				{18}, {69}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {},
				{131}, {132}, {}, {}
			}, 

			
			/* 20 */
			{
				{20}, {70}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {} , 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 21 */
			{
				{21}, {71}, {20}, {20}, {}, {}, {}, {}, 
				{21, 82, 149}, {21, 82, 149}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {22}
			}, 
			
			/* 22 */
			{
				{22, 122, 129}, {73}, {23}, {23}, {}, {}, {}, {},
				{22, 129}, {22, 129}, {}, {}, {}, {}, {}, {},
				{124}, {131}, {124}, {129}, {129}, {23}, {25}, 
				{131}, {132}, {40}, {13}
			}, 
			
			/* 23 */
			{
				{23}, {78}, {28}, {28}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 24 */
			{
				{24}, {74}, {25}, {25}, {}, {}, {}, {}, 
				{4, 24}, {4, 24}, {131}, {131}, {131}, {131}, {74}, {74}, 
				{27}, {27}, {77}, {24}, {24}, {22, 24}, {22, 24}, 
				{131}, {132}, {40, 52}, {4}
			},
			
			/* 25 */
			{
				{25}, {75}, {131}, {131}, {}, {}, {}, {}, 
				{4, 25}, {4, 25}, {131}, {131}, {131}, {131}, {75}, {75}, 
				{27}, {27}, {77}, {25}, {25}, {22, 24}, {22, 24}, 
				{131}, {132}, {40}, {4}
			}, 
			
			
			/* 26 */
			{
				{26}, {76}, {25}, {25}, {}, {}, {}, {}, 
				{4, 26}, {4, 24}, {131}, {131}, {131}, {131}, {76}, {76}, 
				{27}, {27}, {77}, {26}, {26}, {22, 24}, {22, 24}, 
				{131}, {132}, {52}, {40}
			}, 
			
			/* 27 */
			{
				{27}, {77}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 28 */
			{
				{28}, {79}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 29 */
			{
				{30}, {30}, {3, 16}, {3, 16}, {}, {}, {}, {}, 
				{17}, {17}, {12, 15, 138}, {12, 15, 138}, {3, 16}, {3, 16}, {17}, {17}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {17}
			},
			
			/* 30 */
			{
				{40}, {40}, {4, 26}, {4, 26}, {}, {}, {}, {}, 
				{27}, {27}, {22, 25, 139}, {22, 25, 139}, {4, 26}, {4, 26}, {27}, {27}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {27}
			},
			
			/* 31 */
			{
				{31}, {31}, {3, 131}, {3, 131}, {}, {}, {}, {},
				{131}, {131}, {131}, {131}, {131}, {131}, {131}, {131}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 32 */
			{
				{32}, {32}, {4, 131}, {4, 131}, {}, {}, {}, {}, 
				{131}, {131}, {131}, {131}, {131}, {131}, {131}, {131}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 33 */
			{
				{51}, {51}, {131}, {131}, {}, {}, {}, {}, 
				{131}, {131}, {3, 131}, {3, 131}, {131}, {131}, {131}, {131}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 34 */
			{
				{52}, {52}, {131}, {131}, {}, {}, {}, {}, 
				{131}, {131}, {4, 131}, {4, 131}, {131}, {131}, {131}, {131}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 35 */ 
			{
				{53}, {53}, {63}, {63, 145}, {}, {}, {}, {}, 
				{66}, {66}, {13, 11}, {13, 11}, {63}, {63}, {66}, {66}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 36 */
			{
				{53}, {53}, {63}, {63, 145}, {}, {}, {}, {}, 
				{63}, {63}, {13, 11}, {13, 11}, {63}, {63}, {66}, {66},
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 37 */
			{
				{54}, {54}, {73, 146}, {73}, {}, {}, {}, {},
				{76}, {76}, {23, 21}, {23, 21}, {73}, {73}, {76}, {76},
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 38 */ 
			{
				{60}, {10}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 39 */
			{
				{61}, {11}, {10}, {10}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 40 */
			{
				{62}, {81}, {10}, {10}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 41 */
			{
				{63}, {12}, {13}, {13}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			
			/* 42 */
			{
				{64}, {14}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 43 */
			{
				{65}, {15}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 44 */
			{
				{66}, {16}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 45 */
			{
				{67}, {17}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 46 */
			{
				{68}, {13}, {69}, {69}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 47 */
			{
				{69}, {18}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 48 */
			{
				{70}, {20}, {131}, {131}, {}, {}, {}, {},
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 49 */
			{
				{71}, {21}, {20}, {20}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 50 */ 
			{
				{72}, {82}, {20}, {20}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 51 */
			{
				{73}, {22}, {23}, {23}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 52 */
			{
				{74}, {24}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 53 */
			{
				{75}, {25}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 54 */
			{
				{76}, {26}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			},
			
			/* 55 */
			{
				{77}, {27}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 56 */
			{
				{78}, {23}, {79}, {79}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 57 */
			{
				{79}, {28}, {131}, {131}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 58 */
			{
				{81}, {62}, {10}, {10}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 59 */
			{
				{82}, {72}, {20}, {20}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, {}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 60 */
			{
				{83}, {53}, {63}, {63}, {}, {}, {}, {}, 
				{66}, {66}, {13, 11}, {13, 11}, {63}, {63}, {66}, {66}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 61 */
			{
				{84}, {54}, {73}, {73}, {}, {}, {}, {}, 
				{76}, {76}, {23, 21}, {23, 21}, {73}, {73}, {76}, {76}, 
				{}, {}, {}, {}, {}, {}, {}, 
				{131}, {132}, {}, {}
			}, 
			
			/* 62 */
			{
				{85}, {63}, {13}, {13}, {}, {}, {}, {}, 
				{12, 128}, {12, 128}, {131}, {131}, {68}, {68}, {131}, {131}, 
				{123}, {131}, {123}, {15}, {15}, {13}, {15}, 
				{131}, {132}, {30}, {}
			},
			
			/* 63 */
			{
				{86}, {73}, {23}, {23}, {}, {}, {}, {}, 
				{22, 129}, {22, 129}, {131}, {131}, {78}, {78}, {131}, {131}, 
				{124}, {131}, {124}, {25}, {25}, {23}, {25}, 
				{131}, {132}, {40}, {}
			},
			
			/* 64 */
			{
				{87}, {63}, {13}, {13}, {}, {}, {}, {}, 
				{12, 128}, {12, 128}, {131}, {131}, {68}, {68}, {131}, {131}, 
				{123}, {131}, {123}, {15}, {15}, {13}, {15}, 
				{131}, {132}, {30}, {}
			}, 
			
			/* 65 */
			{
				{88}, {73}, {23}, {23}, {}, {}, {}, {}, 
				{22, 129}, {22, 129}, {131}, {131}, {78}, {78}, {131}, {131}, 
				{124}, {131}, {124}, {25}, {25}, {23}, {25}, 
				{131}, {132}, {40}, {}
			}

	};
		
	

		
		
}
