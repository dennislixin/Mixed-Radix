import java.util.*;

public class MixedRadixTest{
  public static void main(String[] args){

  }
}

class MixedRadix{
  List<Integer> bases;
  List<Integer> values;

  public MixedRadix(int[] baseArray){
    if(MixedRadix.isArrayNullOrZero(baseArray))
      throw new IllegalArgumentException("base cannot be null or 0");
    initBases(baseArray);
    initValues();
  }

  private void initBases(int[] baseArray){
    int curBase = 1;
    for(int base : baseArray){
      curBase *= base;
      bases.add(0, curBase);
    }
  }

  private void initValues(){
    if(bases == null || bases.size() == 0)
      throw new IllegalArgumentException("init bases before init values");
    values = new ArrayList<>(Collections.nCopies(bases.size() + 1, 0));
  }

  private void addProcess(int[] anotherValues){
    for(int i=0; i<anotherValues.length; i++){
      int newValue = values.get(i) + anotherValues[i];
      values.set(i, newValue);
    }

    normalizeValues();
  }

  private void normalizeValues(){
    int totalValue = values.get(values.size() - 1);
    for(int i=0; i<bases.size(); i++)
      totalValue += values.get(i) * bases.get(i);

    int i;
    for(i=0; i<bases.size(); i++){
      int newValue = totalValue / bases.get(i);
      values.set(i, newValue);
      totalValue = totalValue % bases.get(i);
    }
    values.set(i, totalValue);
  }

  public int[] add(int[] anotherValues){
    if(!isValidValues(anotherValues))
      throw new IllegalArgumentException("new value is not addable");

    addProcess(anotherValues);
    return getValues();
  }

  public int[] sub(int[] anotherValues){
    if(!isValidValues(anotherValues))
      throw new IllegalArgumentException("new value is not subtractable");

    int[] convertedAnotherValues = new int[anotherValues.length];
    for(int i = 0; i < anotherValues.length; i++)
      convertedAnotherValues[i] = -1 * anotherValues[i];

    addProcess(convertedAnotherValues);
    return getValues();
  }

  public int[] getValues(){
    int[] result = new int[values.size()];
    for(int i=0; i<values.size(); i++)
      result[i] = values.get(i);
    return result;
  }

  private static boolean isArrayNullOrZero(int[] array){
    return array == null || array.length == 0;
  }

  private boolean isValidValues(int[] anotherValues){
    return anotherValues != null && anotherValues.length == values.size();
  }
}
