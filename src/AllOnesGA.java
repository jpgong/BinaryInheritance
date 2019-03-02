
/**
 * 该类用来初始化遗传算法，并作为应用程序的起点,是遗传算法的具体表现
 * @author jpgong
 *
 */
public class AllOnesGA {
	public static void main(String[] args) {
		//创建一个遗传算法对象,将种群中的精英个数设置为2
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.95, 2);
		
		//初始化种群规模,指定个体染色体的长度
		Population population = ga.initPopulation(50);
		
		ga.evalPopulation(population);
		int generation = 1;
		
		while(ga.isTerminationConditionMet(population) == false) {
			//从种群中打印出符合的个体
			System.out.println("Best solutioin:" + population.getFittest(0).toString());
			
			//对种群中的个体进行变异
			population = ga.crossoverPopulation(population);
			
			//对种群中的个体进行交叉产生新的种群
			population = ga.crossoverPopulation(population);
			
			//评估种群中的个体
			ga.evalPopulation(population);
			
			generation++;
		}
		
		System.out.println("Found solution in " + generation + "generations");
		System.out.println("Best solution: " + population.getFittest(0).toString());
	}

}
