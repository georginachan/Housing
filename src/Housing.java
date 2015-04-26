import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;


public class Housing {
	static double[] a = new double[35];
	static double[] b = new double[35];
	static double[] c = new double[35];

	static double theta0 = 1;
	static double theta1 = 1;
	static double theta2 = 1;
	
	static double alpha = 0.3;
	static int m = a.length;
	static double aAverage=0.0,bAverage=0.0,aS=0.0,bS=0.0;
	static double Area=0.0,Floor=0.0;
	
	public static void main(String[] args) throws Exception {

		BufferedReader data = new BufferedReader(new FileReader(new File("E:\\trainingset.txt")));
		String line = "";
		for (int i = 0; i < 35; i++) {
			line = data.readLine().trim();
		    if(!line.matches("\\s*"))
		    {
		String[] array = line.split(",");
		a[i] = Double.valueOf(array[0]);
		b[i] = Double.valueOf(array[1]);
		c[i] = Double.valueOf(array[2]);
		    }
		}
				double aSum = 0;
				for (int i = 0; i < m; i++)
				{
					aSum = aSum + a[i];
				}
				aAverage = aSum / m;

				double[] aTemp = Arrays.copyOf(a, m);
				Arrays.sort(aTemp);
				aS = aTemp[aTemp.length - 1] - aTemp[0];

				for (int i = 0; i < m; i++) {
					a[i] = (a[i] - aAverage) / aS;
				}

				double bSum = 0;
				for (int i = 0; i < m; i++) {
					bSum = bSum + b[i];
				}
				bAverage = bSum / m;

				double[] bTemp = Arrays.copyOf(b, m);
				Arrays.sort(bTemp);
				bS = bTemp[bTemp.length - 1] - bTemp[0];

				for (int i = 0; i < m; i++) {
					b[i] = (b[i] - bAverage) / bS;
				}

				System.out.println(Arrays.toString(a));
				System.out.println(Arrays.toString(b));

				// 姊害涓嬮檷
				for (int i = 0; i < 10000; i++) {

					double sum0 = sum(0);
					double sum1 = sum(1);
					double sum2 = sum(2);

					theta0 = theta0 - alpha / m * sum0;
					theta1 = theta1 - alpha / m * sum1;
					theta2 = theta2 - alpha / m * sum2;

					System.out.println("\r\n" + i);
					System.out.println(cost(theta0, theta1, theta2));
					System.out.println("Theta0=" + theta0);
					System.out.println("Theta1=" + theta1);
					System.out.println("Theta2=" + theta2);

				}
				Price(Area,Floor);

			}

			public static double sum(int thetaIndex) {
				double sum = 0.0;

				if (thetaIndex == 0) {
					for (int i = 0; i < m; i++) {
						sum = sum + (theta0 + theta1 * a[i] + theta2 * b[i] - c[i]);
					}
				} else if (thetaIndex == 1) {
					for (int i = 0; i < m; i++) {
						sum = sum + (theta0 + theta1 * a[i] + theta2 * b[i] - c[i])
								* a[i];
					}
				} else {
					for (int i = 0; i < m; i++) {
						sum = sum + (theta0 + theta1 * a[i] + theta2 * b[i] - c[i])
								* b[i];
					}
				}

				return sum;
			}

			public static double cost(double thetaZero, double thetaOne, double thetaTwo) {

				double cost = 0.0;
				for (int i = 0; i < m; i++) {
					cost = cost+ 1.0/ (2 * m)* Math.pow((thetaZero + thetaOne * a[i] + thetaTwo * b[i] - c[i]),2);
				}
				return cost;

			}
			public static void Price(double Area, double Floor)  
			{
				double Price=0.0;
			@SuppressWarnings("resource")
			Scanner x = new Scanner(System.in);
			System.out.print("请输入房子面积：");
			Area = x.nextDouble();
			Area = (Area -  aAverage) / (aS);
			System.out.print("请输入楼层：");
			Floor = x.nextDouble();
			Floor= (Floor - bAverage) / (bS);
			Price = theta0 + theta1 * Area + theta2 * Floor;
			System.out.print("预测房子的价格为：" + Price);
			}
}
