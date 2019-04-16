
package dps;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ray
 */
public class TokenMaster {
    private ArrayList<Token> tokenList = new ArrayList<>();
            
            
  //this runs through the list of tokens, finds the right one and then calls it's move method
    public void updateTokenLoc(int tokenID, int newX, int newY){
        for (Token token : tokenList) {
            if (tokenID == token.getTokenID()) {
                 token.moveTo(newX, newY);
            }
        }
    }
    
    public void addTokenToList(Token token){
        this.tokenList.add(token);
    }
    
    public void removeTokenFromList(Token token){
        this.tokenList.remove(token);
    }
    
     public List<Token> getTokenList() {
        return tokenList;
    }


}
