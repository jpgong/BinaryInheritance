
/**
 * 该类代表一个候选类，主要负责存储和操作一条染色体
 * 管理并创建种群的个数
 * @author jpgong
 *
 */
public class Individual {
	
	private int[] chromosome;    //一条染色体，其中的每一位代表一个基因
	private double fitness = -1;
	
	/*
	 * 构造方法，直接接收一个整数数组，用它作为染色体
	 */
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	/*
	 * 构造方法，接收一个整数（代表染色体的长度），初始化时创建一条随机的染色体
	 */
	public Individual(int chromosomeLength) {
		// TODO Auto-generated constructor stub
		this.chromosome = new int[chromosomeLength];
		for (int gene = 0; gene < chromosomeLength; gene++) {
			if (Math.random() > 0.5) {
				this.setGene(gene, 1);
			}else {
				this.setGene(gene, 0);
			}
		}
	}
	public int[] getChromosome() {
		return this.chromosome;
	}
	public int getChromesomeLength() {
		return this.chromosome.length;
	}
	
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}
	public int getGene(int offset) {
		return this.chromosome[offset];
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int gene = 0; gene < chromosome.length; gene++) {
			sb.append(this.chromosome[gene]);
		}
		return sb.toString();
	}
   
	
	
	

}
