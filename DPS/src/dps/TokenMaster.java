
package dps;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ray
 */
public class TokenMaster {
    public ArrayList<token> tokenList = new ArrayList<>();
            
            
  //this runs through the list of tokens, finds the right one and then calls it's move method
    public void updateTokenLoc(int tokenID, int newX, int newY){
        for (token token : tokenList) {
            if (tokenID == token.getTokenID()) {
                 token.moveTo(newX, newY);
            }
        }
    }
    
    public void addTokenToList(token t){
        this.tokenList.add(t);
    }
    
    public void removeTokenFromList(token t){
        this.tokenList.remove(t);
    }
    
     public List<token> getTokenList() {
        return tokenList;
    }


}
