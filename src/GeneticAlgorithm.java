
/**
 * �ڸ����а����Ŵ��㷨����Ĳ�������ķ����ͱ���
 * �������������桢���졢��Ӧ����������ֹ���������߼�
 * @author jpgong
 *
 */

public class GeneticAlgorithm {
	
	private int populationSize;  //��Ⱥ��ģ
	private double mutationRate;   //������
	private double crossoverRate;    //������
	private double elitismCount;   //��Ӣ��Ա��
	
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
	 * ����Ⱥ�е�ÿ�������������Ӧ��ֵ�����洢�Ա㽫��ʹ��
	 * �Ŵ��㷨ͨ��ѡ���������������̣��õ����õĸ��壬������Ӧ�Ⱥ���ʹ����ѡ���Ϊ����
	 * ÿ���ض����Ż����⣬����Ҫһ���ر𿪷�����Ӧ�Ⱥ������ڸ������У�ֻ��Ҫ����Ⱦɫ����1�ĸ���
	 * ����������Ⱦɫ����1�ĸ�����Ȼ�����Ⱦɫ��ĳ��ȣ�ʹ�����񻯣���0��1֮��
	 */
	public double calcFitness(Individual individual) {
		//��ȷȾɫ��ĸ�������
		int corretGenes = 0;
		
		//ѭ��Ⱦɫ���еĻ���
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
	 * ������Ⱥ�е�ÿ�����岢�������ǣ�����ÿ���������calcFitness��
	 */
	public void evalPopulation(Population population) {
		double populationFitness = 0;
		
		for (Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual);
		}
		
		population.setPopulationFitness(populationFitness);
	}
	
	/*
	 * �����ֹ�����Ƿ��ѷ���
	 * �����������Ⱥ���κθ������Ӧ��Ϊ1���ͷ���ture
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
	 * ����Ⱥ��ѡ�����ķ��������̶�ѡ��
	 * �������������Ӧ��Խ�ߣ���������ռ�ݵĿռ��Խ��
	 */
	public Individual selectParent(Population population) {
		//�����Ⱥ�е����и���
		Individual individuals[] = population.getIndividuals();
		
		double populationFitness = population.getPopulationFitness();
		//�涨һ�����̶����λ�ã�ѡ��һ������0����Ⱥ����Ӧ�ȵ������
		double rouletteWheelPosition = Math.random()*populationFitness;
		
		//Ѱ�Ҹ���
		double spinWheel = 0;
		//������ÿ�����壬ͬʱ�ۼ����ǵ���Ӧ��ֵ��ֱ����ʼѡ������λ��
		for (Individual individual : individuals) {
			spinWheel +=individual.getFitness();
			if (spinWheel >= rouletteWheelPosition) {
				return individual;
			}
		}
		return individuals[population.size()-1];
		
	}
	
	/*
	 * ������Ⱥ���淽��
	 * ����ʵ�ֵĽ��淽���Ǿ��Ƚ��棬��������е�ÿ��������50%�Ļ������Ե�һ���״��͵ڶ����״�
	 * �÷����������Ⱥ��ÿ��������ɽ�����̺󣬽��淽��������һ������Ⱥ
	 */
	public Population crossoverPopulation(Population population) {
		//Ϊ��һ������һ���µĿ���Ⱥ
		Population newPopulation = new Population(population.size());
		
		//������Ⱥ�����ý�����������ÿ������Ľ���
		//������岻�������棬����ֱ�Ӽ�����һ����Ⱥ������ʹ���һ���µĸ���
		//���Ⱦɫ�����䷽���Ǳ����״�Ⱦɫ�壬�����ÿ���״�ѡ����򣬼�������Ⱦɫ��
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);
			
			//�Ƿ�����������н���?
			//�����������������Ҹ����������Ǿ�Ӣ���Ž��н���
			//��Ⱥ�еĸ����Ѿ��������ǵ���Ӧ�����������ǿ��ĸ�������ֵ��С
			//��ˣ������Ҫ3����Ӣ���壬Ӧ����������0~2���⽫������ǿ��ĸ���,ֱ�Ӵ��ݵ���һ��
			if (this.crossoverRate > Math.random() && populationIndex > this.elitismCount) {
				Individual offsping = new Individual(parent1.getChromesomeLength());
				
				//Ѱ�ҵڶ�������
				Individual parent2 = selectParent(population);
				
				for (int geneIndex = 0; geneIndex < parent1.getChromesomeLength(); geneIndex++) {
					//ʹ�ø���1��һ�����͸���2��һ�����
					if (Math.random() < 0.5) {
						offsping.setGene(geneIndex, parent1.getGene(geneIndex));
					}else {
						offsping.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}
				
				//�������µĸ��嵽��Ⱥ��
				newPopulation.setIndividual(populationIndex, offsping);
			}else {
				//�����н�����Ӹ��嵽�µ���Ⱥ��
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		
		return newPopulation;
	}
	
	/*
	 * ��Ⱥ�����㷨
	 * ��������λ��ת������
	 */
	public Population mutatePopulation(Population population) {
		//Ϊ����ĸ��崴��һ���µĿ���Ⱥ
		Population newPopulation = new Population(this.populationSize);
		
		//����������Ⱥ
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);
			
			//����ÿ�������Ⱦɫ��
			for (int geneIndex = 0; geneIndex < individual.getChromesomeLength(); geneIndex++) {
				//�����һ����Ӣ��������������
				if (populationIndex >= this.elitismCount) {
					//�ж���������Ƿ���Ҫ����
					//���ڱ����ʣ�����ÿ�������Ƿ����λ��ת����
					if (this.mutationRate > Math.random()) {
						//����һ���µĻ���
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						//�������
						individual.setGene(geneIndex, newGene);
					}
				}
			}
			
			//���һ���µĸ��嵽��Ⱥ��
			newPopulation.setIndividual(populationIndex, individual);
			
		}
		
		//����һ���������Ⱥ
		return newPopulation;
	}
	
	
	
	
	
	
	
	
	
	
	
	
			

}
