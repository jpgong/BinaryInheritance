import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * 该类中提供管理群体中一组个体所需的功能
 * 主要功能是保存由个体构成的一个数组，能够通过类方法方便的访问
 * @author jpgong
 *
 */
public class Population {
	private Individual population[];
	private double populationFitness = -1;
	
	public Population(int populationSize) {
		this.population = new Individual[populationSize];
	}
	
	public Population(int populationSize, int chromosomeLength) {
		this.population = new Individual[populationSize];
		
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			Individual individual = new Individual(chromosomeLength);
			this.population[individualCount] = individual;
		}
	}

	public Individual[] getIndividuals() {
		return population;
	}
	
	public Individual getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return -1;
				}else if (o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});
		
		return this.population[offset];
	}

	public double getPopulationFitness() {
		return populationFitness;
	}

	public void setPopulationFitness(double populationFitness) {
		this.populationFitness = populationFitness;
	}
	
	public int size() {
		return population.length;
	}
	
	public void setIndividual(int offset, Individual individual) {
		population[offset] = individual;
	}
	
	public Individual getIndividual(int offset) {
		return population[offset];
	}
	
	public void shuffle() {
		Random rnd = new Random();
		for (int i = population.length-1; i > 0; i--) {
			int index = rnd.nextInt(i+1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}
	
}
