package mainPack;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Flower {

	List<Double> properties;
	String name;

	public Flower(List<Double> properties, String name){

		this.properties = properties;
		this.name = name;

	}
	public String recognize(int k, List<Flower> trainingSet)  {

		Distance[] kNearest = new Distance[k];

		for(Flower tFlower : trainingSet){

			double val=0;

			for(int op = 0 ; op < properties.size() ; ++op)
				val+= Math.pow(properties.get(op)-tFlower.properties.get(op),2);

			double valSq = Math.sqrt(val);
			Distance distance = new Distance(tFlower,valSq);

			for(int op = 0 ; op < k; ++ op) {
				if (kNearest[op] == null) {
					kNearest[op] = distance;
					break;
				} else if (kNearest[op].getValue() > distance.getValue()){
					Distance tmp = kNearest[op];
					kNearest[op] = distance;
					distance = tmp;

				}

			}

		}
		Map<String, Integer> map = new LinkedHashMap<>();
		for(int op = 0 ; op < kNearest.length ; ++op){
			if(kNearest[op] != null) {
				if (map.containsKey(kNearest[op].getFlower().name)) {

					Integer i = map.get(kNearest[op].getFlower().name);
					i += 1;
					map.replace(kNearest[op].getFlower().name, i);

				} else
					map.put(kNearest[op].getFlower().name, 1);
			}
		}

		String result = "";
		int largest = -1;

		for(Map.Entry<String, Integer> entry : map.entrySet() ){

			if(entry.getValue() > largest){

				result = entry.getKey();
				largest = entry.getValue();

			}

		}

		return result;
	}



	@Override
	public String toString() {
		return "Flower{" +
				"properties=" + properties +
				", name='" + name + '\'' +
				'}';
	}


	private class Distance {

		private Flower flower;
		private double value;

		public Distance(Flower name,double value){
			this.flower = name;
			this.value = value;
		}
		public double getValue(){
			return value;
		}
		public Flower getFlower(){
			return flower;
		}

		@Override
		public String toString() {
			return "Distance{" +
					"name='" + name + '\n' +
					", value=" + value + '\n' +
					", hash=" + this.hashCode() + '\n' +
					'}' +'\n';
		}

	}
}
