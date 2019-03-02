
/**
 * 在该类中包含遗传算法本身的操作所需的方法和变量
 * 这个类包括处理交叉、变异、适应度评估和终止条件检查的逻辑
 * @author jpgong
 *
 */

public class GeneticAlgorithm {
	
	private int populationSize;  //种群规模
	private double mutationRate;   //变异率
	private double crossoverRate;    //交叉率
	private double elitismCount;   //精英成员数
	
	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, double elitismCount) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
	}
	
	public Population initPopulation(int chromosomeLength) {
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}
	
	/*
	 * 对种群中的每个个体计算其适应度值，并存储以便将来使用
	 * 遗传算法通过选择来引导进化过程，得到更好的个体，正是适应度函数使这种选择成为可能
	 * 每个特定的优化问题，都需要一个特别开发的适应度函数。在该问题中，只需要计算染色体中1的个数
	 * 方法：计算染色体中1的个数，然后除以染色体的长度，使输出规格化，在0和1之间
	 */
	public double calcFitness(Individual individual) {
		//正确染色体的跟踪数量
		int corretGenes = 0;
		
		//循环染色体中的基因
		for (int geneIndex = 0; geneIndex < individual.getChromesomeLength(); geneIndex++) {
			if (individual.getGene(geneIndex) == 1) {
				corretGenes += 1;
			}
		}
		
		double fitness = (double)corretGenes/individual.getChromesomeLength();
		
		individual.setFitness(fitness);
		
		return fitness;
	}
	
	/*
	 * 遍历种群中的每个个体并评估它们（即对每个个体调用calcFitness）
	 */
	public void evalPopulation(Population population) {
		double populationFitness = 0;
		
		for (Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual);
		}
		
		population.setPopulationFitness(populationFitness);
	}
	
	/*
	 * 检查终止条件是否已发生
	 * 方法：如果种群中任何个体的适应度为1，就返回ture
	 */
	public boolean isTerminationConditionMet(Population population) {
		for (Individual individual : population.getIndividuals()) {
			if (individual.getFitness() == 1) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * 从种群中选择个体的方法：轮盘赌选择
	 * 方法：个体的适应度越高，在轮盘中占据的空间就越多
	 */
	public Individual selectParent(Population population) {
		//获得种群中的所有个体
		Individual individuals[] = population.getIndividuals();
		
		double populationFitness = population.getPopulationFitness();
		//规定一个轮盘赌随机位置，选择一个介于0和种群总适应度的随机数
		double rouletteWheelPosition = Math.random()*populationFitness;
		
		//寻找个体
		double spinWheel = 0;
		//逐个检查每个个体，同时累加他们的适应度值，直到起始选择的随机位置
		for (Individual individual : individuals) {
			spinWheel +=individual.getFitness();
			if (spinWheel >= rouletteWheelPosition) {
				return individual;
			}
		}
		return individuals[population.size()-1];
		
	}
	
	/*
	 * 创建种群交叉方法
	 * 这里实现的交叉方法是均匀交叉，这样后代中的每个基因都有50%的机会来自第一个亲代和第二个亲代
	 * 该方法是针对种群的每个个体完成交叉过程后，交叉方法返回下一代的种群
	 */
	public Population crossoverPopulation(Population population) {
		//为下一代创建一个新的空种群
		Population newPopulation = new Population(population.size());
		
		//遍历种群，利用交叉率来考虑每个个体的交叉
		//如果个体不经过交叉，它就直接加入下一个种群，否则就创建一个新的个体
		//后代染色体的填充方法是遍历亲代染色体，随机从每个亲代选择基因，加入后代的染色体
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			
			//是否和这个个体进行交叉?
			//仅当交叉条件满足且个体条件不是精英，才进行交叉
			//种群中的个体已经按照他们的适应度排序，因此最强大的个体索引值最小
			//因此，如果需要3个精英个体，应该跳过索引0~2，这将保留最强大的个体,直接传递到下一代
			if (this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
				Individual offsping = new Individual(parent1.getChromesomeLength());
				
				//寻找第二个个体
				Individual parent2 = selectParent(population);
				
				for (int geneIndex = 0; geneIndex < parent1.getChromesomeLength(); geneIndex++) {
					//使用个体1的一半基因和个体2的一半基因
					if (Math.random() < 0.5) {
						offsping.setGene(geneIndex, parent1.getGene(geneIndex));
					}else {
						offsping.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}
				
				//添加这个新的个体到种群中
				newPopulation.setIndividual(populationIndex, offsping);
			}else {
				//不进行交叉添加个体到新的种群中
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		
		return newPopulation;
	}
	
	/*
	 * 种群变异算法
	 * 方法：“位翻转”变异
	 */
	public Population mutatePopulation(Population population) {
		//为变异的个体创建一个新的空种群
		Population newPopulation = new Population(this.populationSize);
		
		//遍历整个种群
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);
			
			//遍历每个个体的染色体
			for (int geneIndex = 0; geneIndex < individual.getChromesomeLength(); geneIndex++) {
				//如果是一个精英个体则跳过变异
				if (populationIndex >= this.elitismCount) {
					//判断这个基因是否需要变异
					//基于变异率，考虑每个基因是否进行位翻转变异
					if (this.mutationRate > Math.random()) {
						//生成一个新的基因
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						//变异基因
						individual.setGene(geneIndex, newGene);
					}
				}
			}
			
			//添加一个新的个体到种群中
			newPopulation.setIndividual(populationIndex, individual);
			
		}
		
		//返回一个变异的种群
		return newPopulation;
	}
	
	
	
	
	
	
	
	
	
	
	
	
			

}
