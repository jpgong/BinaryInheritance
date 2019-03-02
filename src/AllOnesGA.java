
/**
 * ����������ʼ���Ŵ��㷨������ΪӦ�ó�������,���Ŵ��㷨�ľ������
 * @author jpgong
 *
 */
public class AllOnesGA {
	public static void main(String[] args) {
		//����һ���Ŵ��㷨����,����Ⱥ�еľ�Ӣ��������Ϊ2
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.001, 0.95, 2);
		
		//��ʼ����Ⱥ��ģ,ָ������Ⱦɫ��ĳ���
		Population population = ga.initPopulation(50);
		
		ga.evalPopulation(population);
		int generation = 1;
		
		while(ga.isTerminationConditionMet(population) == false) {
			//����Ⱥ�д�ӡ�����ϵĸ���
			System.out.println("Best solutioin:" + population.getFittest(0).toString());
			
			//����Ⱥ�еĸ�����б���
			population = ga.crossoverPopulation(population);
			
			//����Ⱥ�еĸ�����н�������µ���Ⱥ
			population = ga.crossoverPopulation(population);
			
			//������Ⱥ�еĸ���
			ga.evalPopulation(population);
			
			generation++;
		}
		
		System.out.println("Found solution in " + generation + "generations");
		System.out.println("Best solution: " + population.getFittest(0).toString());
	}

}
