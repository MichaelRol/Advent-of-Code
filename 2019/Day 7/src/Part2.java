import java.util.ArrayList;
import java.util.List;

public class Part2 {
	public static int run(ArrayList<Integer> instructions) {
		List<List<Integer>> perms = permute(new int[]{5,6,7,8,9});
		int max = 0;
		for (List<Integer> perm : perms) {
			Amp2 A = new Amp2(perm.get(0), (ArrayList<Integer>) instructions.clone());
			Amp2 B = new Amp2(perm.get(1), (ArrayList<Integer>) instructions.clone());
			Amp2 C = new Amp2(perm.get(2), (ArrayList<Integer>) instructions.clone());
			Amp2 D = new Amp2(perm.get(3), (ArrayList<Integer>) instructions.clone());
			Amp2 E = new Amp2(perm.get(4), (ArrayList<Integer>) instructions.clone());
			A.run();
			B.run();
			C.run();
			D.run();
			E.run();
			A.input = 0;
			while (E.halt == false) {
				A.run();
				B.input = A.output;
				B.run();
				C.input = B.output;
				C.run();
				D.input = C.output;
				D.run();
				E.input = D.output;
				E.run();
				A.input = E.output;
			}
			if (E.output > max) {
				max = E.output;
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
