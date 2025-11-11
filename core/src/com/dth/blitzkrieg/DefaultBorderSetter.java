package com.dth.blitzkrieg;

public class DefaultBorderSetter implements BorderSetter {

	@Override
	public void setBorders(Province[] provinces) {
		provinces[0].addNeighbour(provinces[29]);
		provinces[0].addNeighbour(provinces[4]);
		provinces[0].addNeighbour(provinces[2]);
		
		provinces[1].addNeighbour(provinces[2]);
		provinces[1].addNeighbour(provinces[3]);
		provinces[1].addNeighbour(provinces[5]);
		provinces[1].addNeighbour(provinces[23]);
		
		provinces[2].addNeighbour(provinces[0]);
		provinces[2].addNeighbour(provinces[4]);
		provinces[2].addNeighbour(provinces[3]);
		provinces[2].addNeighbour(provinces[1]);
		
		provinces[3].addNeighbour(provinces[4]);
		provinces[3].addNeighbour(provinces[6]);
		provinces[3].addNeighbour(provinces[5]);
		provinces[3].addNeighbour(provinces[1]);
		provinces[3].addNeighbour(provinces[2]);
		
		provinces[4].addNeighbour(provinces[6]);
		provinces[4].addNeighbour(provinces[3]);
		provinces[4].addNeighbour(provinces[2]);
		provinces[4].addNeighbour(provinces[0]);
		
		provinces[5].addNeighbour(provinces[3]);
		provinces[5].addNeighbour(provinces[6]);
		provinces[5].addNeighbour(provinces[7]);
		provinces[5].addNeighbour(provinces[24]);
		provinces[5].addNeighbour(provinces[23]);
		provinces[5].addNeighbour(provinces[1]);
		
		provinces[6].addNeighbour(provinces[8]);
		provinces[6].addNeighbour(provinces[9]);
		provinces[6].addNeighbour(provinces[7]);
		provinces[6].addNeighbour(provinces[5]);
		provinces[6].addNeighbour(provinces[3]);
		provinces[6].addNeighbour(provinces[4]);
		
		provinces[7].addNeighbour(provinces[6]);
		provinces[7].addNeighbour(provinces[9]);
		provinces[7].addNeighbour(provinces[15]);
		provinces[7].addNeighbour(provinces[25]);
		provinces[7].addNeighbour(provinces[24]);
		provinces[7].addNeighbour(provinces[5]);
		
		provinces[8].addNeighbour(provinces[10]);
		provinces[8].addNeighbour(provinces[14]);
		provinces[8].addNeighbour(provinces[9]);
		provinces[8].addNeighbour(provinces[6]);
		
		provinces[9].addNeighbour(provinces[8]);
		provinces[9].addNeighbour(provinces[14]);
		provinces[9].addNeighbour(provinces[15]);
		provinces[9].addNeighbour(provinces[7]);
		provinces[9].addNeighbour(provinces[6]);
		
		provinces[10].addNeighbour(provinces[11]);
		provinces[10].addNeighbour(provinces[12]);
		provinces[10].addNeighbour(provinces[13]);
		provinces[10].addNeighbour(provinces[14]);
		provinces[10].addNeighbour(provinces[8]);
		
		provinces[11].addNeighbour(provinces[16]);
		provinces[11].addNeighbour(provinces[12]);
		provinces[11].addNeighbour(provinces[10]);
		
		provinces[12].addNeighbour(provinces[11]);
		provinces[12].addNeighbour(provinces[16]);
		provinces[12].addNeighbour(provinces[13]);
		provinces[12].addNeighbour(provinces[10]);
		
		provinces[13].addNeighbour(provinces[12]);
		provinces[13].addNeighbour(provinces[16]);
		provinces[13].addNeighbour(provinces[17]);
		provinces[13].addNeighbour(provinces[14]);
		provinces[13].addNeighbour(provinces[10]);
		
		provinces[14].addNeighbour(provinces[13]);
		provinces[14].addNeighbour(provinces[18]);
		provinces[14].addNeighbour(provinces[15]);
		provinces[14].addNeighbour(provinces[9]);
		provinces[14].addNeighbour(provinces[8]);
		provinces[14].addNeighbour(provinces[10]);
		
		provinces[15].addNeighbour(provinces[9]);
		provinces[15].addNeighbour(provinces[14]);
		provinces[15].addNeighbour(provinces[18]);
		provinces[15].addNeighbour(provinces[7]);
		
		provinces[16].addNeighbour(provinces[33]);
		provinces[16].addNeighbour(provinces[17]);
		provinces[16].addNeighbour(provinces[13]);
		provinces[16].addNeighbour(provinces[12]);
		provinces[16].addNeighbour(provinces[11]);
		
		provinces[17].addNeighbour(provinces[16]);
		provinces[17].addNeighbour(provinces[13]);
		
		provinces[18].addNeighbour(provinces[14]);
		provinces[18].addNeighbour(provinces[19]);
		provinces[18].addNeighbour(provinces[15]);
		
		provinces[19].addNeighbour(provinces[18]);
		provinces[19].addNeighbour(provinces[20]);
		provinces[19].addNeighbour(provinces[21]);
		
		provinces[20].addNeighbour(provinces[19]);
		provinces[20].addNeighbour(provinces[21]);
		provinces[20].addNeighbour(provinces[22]);
		
		provinces[21].addNeighbour(provinces[19]);
		provinces[21].addNeighbour(provinces[20]);
		provinces[21].addNeighbour(provinces[22]);
		
		provinces[22].addNeighbour(provinces[21]);
		provinces[22].addNeighbour(provinces[20]);
		
		provinces[23].addNeighbour(provinces[1]);
		provinces[23].addNeighbour(provinces[5]);
		provinces[23].addNeighbour(provinces[24]);
		provinces[23].addNeighbour(provinces[25]);
		provinces[23].addNeighbour(provinces[26]);
		provinces[23].addNeighbour(provinces[40]);
		
		provinces[24].addNeighbour(provinces[5]);
		provinces[24].addNeighbour(provinces[7]);
		provinces[24].addNeighbour(provinces[25]);
		provinces[24].addNeighbour(provinces[23]);
		
		provinces[25].addNeighbour(provinces[24]);
		provinces[25].addNeighbour(provinces[7]);
		provinces[25].addNeighbour(provinces[28]);
		provinces[25].addNeighbour(provinces[27]);
		provinces[25].addNeighbour(provinces[26]);
		provinces[25].addNeighbour(provinces[23]);
		
		provinces[26].addNeighbour(provinces[23]);
		provinces[26].addNeighbour(provinces[25]);
		provinces[26].addNeighbour(provinces[27]);
		
		provinces[27].addNeighbour(provinces[26]);
		provinces[27].addNeighbour(provinces[25]);
		provinces[27].addNeighbour(provinces[28]);
		
		provinces[28].addNeighbour(provinces[25]);
		provinces[28].addNeighbour(provinces[27]);
		
		provinces[29].addNeighbour(provinces[0]);
		provinces[29].addNeighbour(provinces[30]);
		provinces[29].addNeighbour(provinces[31]);
		provinces[29].addNeighbour(provinces[32]);
		
		provinces[30].addNeighbour(provinces[29]);
		provinces[30].addNeighbour(provinces[36]);
		provinces[30].addNeighbour(provinces[31]);
		
		provinces[31].addNeighbour(provinces[32]);
		provinces[31].addNeighbour(provinces[29]);
		provinces[31].addNeighbour(provinces[30]);
		provinces[31].addNeighbour(provinces[36]);
		provinces[31].addNeighbour(provinces[35]);
		provinces[31].addNeighbour(provinces[34]);
		
		provinces[32].addNeighbour(provinces[29]);
		provinces[32].addNeighbour(provinces[31]);
		provinces[32].addNeighbour(provinces[34]);
		provinces[32].addNeighbour(provinces[33]);
		
		provinces[33].addNeighbour(provinces[32]);
		provinces[33].addNeighbour(provinces[34]);
		provinces[33].addNeighbour(provinces[16]);
		
		provinces[34].addNeighbour(provinces[32]);
		provinces[34].addNeighbour(provinces[31]);
		provinces[34].addNeighbour(provinces[35]);
		provinces[34].addNeighbour(provinces[33]);
		
		provinces[35].addNeighbour(provinces[34]);
		provinces[35].addNeighbour(provinces[31]);
		provinces[35].addNeighbour(provinces[36]);
		provinces[35].addNeighbour(provinces[37]);
		
		provinces[36].addNeighbour(provinces[31]);
		provinces[36].addNeighbour(provinces[30]);
		provinces[36].addNeighbour(provinces[37]);
		provinces[36].addNeighbour(provinces[35]);
		
		provinces[37].addNeighbour(provinces[35]);
		provinces[37].addNeighbour(provinces[36]);
		provinces[37].addNeighbour(provinces[38]);
		
		provinces[38].addNeighbour(provinces[37]);
		provinces[38].addNeighbour(provinces[40]);
		provinces[38].addNeighbour(provinces[39]);
		
		provinces[39].addNeighbour(provinces[38]);
		provinces[39].addNeighbour(provinces[40]);
		provinces[39].addNeighbour(provinces[41]);
		
		provinces[40].addNeighbour(provinces[38]);
		provinces[40].addNeighbour(provinces[23]);
		provinces[40].addNeighbour(provinces[41]);
		provinces[40].addNeighbour(provinces[39]);
		
		provinces[41].addNeighbour(provinces[39]);
		provinces[41].addNeighbour(provinces[40]);
	}

}
