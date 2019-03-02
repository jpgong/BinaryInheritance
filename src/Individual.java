
/**
 * �������һ����ѡ�࣬��Ҫ����洢�Ͳ���һ��Ⱦɫ��
 * ����������Ⱥ�ĸ���
 * @author jpgong
 *
 */
public class Individual {
	
	private int[] chromosome;    //һ��Ⱦɫ�壬���е�ÿһλ����һ������
	private double fitness = -1;
	
	/*
	 * ���췽����ֱ�ӽ���һ���������飬������ΪȾɫ��
	 */
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	/*
	 * ���췽��������һ������������Ⱦɫ��ĳ��ȣ�����ʼ��ʱ����һ�������Ⱦɫ��
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
