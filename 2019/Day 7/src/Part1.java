import java.util.ArrayList;
import java.util.List;

public class Part1 {
	public static int run(ArrayList<Integer> instructions) {
		List<List<Integer>> perms = permute(new int[]{0,1,2,3,4});
		int max = 0;
		Amp A = new Amp(0, 0);
		Amp B = new Amp(0, 0);
		Amp C = new Amp(0, 0);
		Amp D = new Amp(0, 0);
		Amp E = new Amp(0, 0);
		for (List<Integer> perm : perms) {
			A.input = 0;
			A.phase = perm.get(0);
			int bInput = A.run(instructions);
			B.input = bInput;
			B.phase = perm.get(1);
			int cInput = B.run(instructions);
			C.input = cInput;
			C.phase = perm.get(2);
			int dInput = C.run(instructions);
			D.input = dInput;
			D.phase = perm.get(3);
			int eInput = D.run(instructions);
			E.input = eInput;
			E.phase = perm.get(4);
			int output = E.run(instructions);
			if (output > max) {
				max = output;
			}
		}
		return max;
	}
	
	public static List<List<Integer>> permute(int[] arr) {
        List<List<Integer>> list = new ArrayList<>();
        permuteHelper(list, new ArrayList<>(), arr);
        return list;
    }
 
    private static void permuteHelper(List<List<Integer>> list, List<Integer> resultList, int [] arr){
 
        if(resultList.size() == arr.length){
            list.add(new ArrayList<>(resultList));
        } 
        else{
            for(int i = 0; i < arr.length; i++){ 
 
                if(resultList.contains(arr[i])) 
                {
                    continue; 
                }
                resultList.add(arr[i]);
                permuteHelper(list, resultList, arr);
                resultList.remove(resultList.size() - 1);
            }
        }
    } 
	 
}
