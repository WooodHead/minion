/* The following code was generated by JFlex 1.4.1 on 12/08/08 11:00 */

/* Lexer for ESSENCE' */
package translator.essencePrimeParser;

import java_cup.runtime.*;



/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1
 * on 12/08/08 11:00 from the specification file
 * <tt>EssencePrimeLexer.flex</tt>
 */
public class EssencePrimeLexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\10\1\3\1\2\1\0\1\3\1\1\16\10\4\0\1\40\1\62"+
    "\2\0\1\4\1\70\1\0\1\15\1\50\1\51\1\65\1\63\1\54"+
    "\1\64\1\17\1\66\1\5\1\16\10\6\1\55\1\0\1\57\1\61"+
    "\1\60\2\0\2\7\1\14\1\32\1\11\10\7\1\13\4\7\1\12"+
    "\7\7\1\52\1\71\1\53\1\67\1\7\1\0\1\21\1\26\1\46"+
    "\1\27\1\25\1\31\1\23\1\47\1\30\2\7\1\20\1\36\1\22"+
    "\1\37\1\43\1\7\1\33\1\35\1\34\1\24\1\45\1\41\1\44"+
    "\1\42\1\7\1\0\1\56\2\0\41\10\2\0\4\7\4\0\1\7"+
    "\2\0\1\10\7\0\1\7\4\0\1\7\5\0\27\7\1\0\37\7"+
    "\1\0\u013f\7\31\0\162\7\4\0\14\7\16\0\5\7\11\0\1\7"+
    "\21\0\130\10\5\0\23\10\12\0\1\7\13\0\1\7\1\0\3\7"+
    "\1\0\1\7\1\0\24\7\1\0\54\7\1\0\46\7\1\0\5\7"+
    "\4\0\202\7\1\0\4\10\3\0\105\7\1\0\46\7\2\0\2\7"+
    "\6\0\20\7\41\0\46\7\2\0\1\7\7\0\47\7\11\0\21\10"+
    "\1\0\27\10\1\0\3\10\1\0\1\10\1\0\2\10\1\0\1\10"+
    "\13\0\33\7\5\0\3\7\15\0\4\10\14\0\6\10\13\0\32\7"+
    "\5\0\13\7\16\10\7\0\12\10\4\0\2\7\1\10\143\7\1\0"+
    "\1\7\10\10\1\0\6\10\2\7\2\10\1\0\4\10\2\7\12\10"+
    "\3\7\2\0\1\7\17\0\1\10\1\7\1\10\36\7\33\10\2\0"+
    "\3\7\60\0\46\7\13\10\1\7\u014f\0\3\10\66\7\2\0\1\10"+
    "\1\7\20\10\2\0\1\7\4\10\3\0\12\7\2\10\2\0\12\10"+
    "\21\0\3\10\1\0\10\7\2\0\2\7\2\0\26\7\1\0\7\7"+
    "\1\0\1\7\3\0\4\7\2\0\1\10\1\7\7\10\2\0\2\10"+
    "\2\0\3\10\11\0\1\10\4\0\2\7\1\0\3\7\2\10\2\0"+
    "\12\10\4\7\15\0\3\10\1\0\6\7\4\0\2\7\2\0\26\7"+
    "\1\0\7\7\1\0\2\7\1\0\2\7\1\0\2\7\2\0\1\10"+
    "\1\0\5\10\4\0\2\10\2\0\3\10\13\0\4\7\1\0\1\7"+
    "\7\0\14\10\3\7\14\0\3\10\1\0\11\7\1\0\3\7\1\0"+
    "\26\7\1\0\7\7\1\0\2\7\1\0\5\7\2\0\1\10\1\7"+
    "\10\10\1\0\3\10\1\0\3\10\2\0\1\7\17\0\2\7\2\10"+
    "\2\0\12\10\1\0\1\7\17\0\3\10\1\0\10\7\2\0\2\7"+
    "\2\0\26\7\1\0\7\7\1\0\2\7\1\0\5\7\2\0\1\10"+
    "\1\7\6\10\3\0\2\10\2\0\3\10\10\0\2\10\4\0\2\7"+
    "\1\0\3\7\4\0\12\10\1\0\1\7\20\0\1\10\1\7\1\0"+
    "\6\7\3\0\3\7\1\0\4\7\3\0\2\7\1\0\1\7\1\0"+
    "\2\7\3\0\2\7\3\0\3\7\3\0\10\7\1\0\3\7\4\0"+
    "\5\10\3\0\3\10\1\0\4\10\11\0\1\10\17\0\11\10\11\0"+
    "\1\7\7\0\3\10\1\0\10\7\1\0\3\7\1\0\27\7\1\0"+
    "\12\7\1\0\5\7\4\0\7\10\1\0\3\10\1\0\4\10\7\0"+
    "\2\10\11\0\2\7\4\0\12\10\22\0\2\10\1\0\10\7\1\0"+
    "\3\7\1\0\27\7\1\0\12\7\1\0\5\7\2\0\1\10\1\7"+
    "\7\10\1\0\3\10\1\0\4\10\7\0\2\10\7\0\1\7\1\0"+
    "\2\7\4\0\12\10\22\0\2\10\1\0\10\7\1\0\3\7\1\0"+
    "\27\7\1\0\20\7\4\0\6\10\2\0\3\10\1\0\4\10\11\0"+
    "\1\10\10\0\2\7\4\0\12\10\22\0\2\10\1\0\22\7\3\0"+
    "\30\7\1\0\11\7\1\0\1\7\2\0\7\7\3\0\1\10\4\0"+
    "\6\10\1\0\1\10\1\0\10\10\22\0\2\10\15\0\60\7\1\10"+
    "\2\7\7\10\4\0\10\7\10\10\1\0\12\10\47\0\2\7\1\0"+
    "\1\7\2\0\2\7\1\0\1\7\2\0\1\7\6\0\4\7\1\0"+
    "\7\7\1\0\3\7\1\0\1\7\1\0\1\7\2\0\2\7\1\0"+
    "\4\7\1\10\2\7\6\10\1\0\2\10\1\7\2\0\5\7\1\0"+
    "\1\7\1\0\6\10\2\0\12\10\2\0\2\7\42\0\1\7\27\0"+
    "\2\10\6\0\12\10\13\0\1\10\1\0\1\10\1\0\1\10\4\0"+
    "\2\10\10\7\1\0\42\7\6\0\24\10\1\0\2\10\4\7\4\0"+
    "\10\10\1\0\44\10\11\0\1\10\71\0\42\7\1\0\5\7\1\0"+
    "\2\7\1\0\7\10\3\0\4\10\6\0\12\10\6\0\6\7\4\10"+
    "\106\0\46\7\12\0\51\7\7\0\132\7\5\0\104\7\5\0\122\7"+
    "\6\0\7\7\1\0\77\7\1\0\1\7\1\0\4\7\2\0\7\7"+
    "\1\0\1\7\1\0\4\7\2\0\47\7\1\0\1\7\1\0\4\7"+
    "\2\0\37\7\1\0\1\7\1\0\4\7\2\0\7\7\1\0\1\7"+
    "\1\0\4\7\2\0\7\7\1\0\7\7\1\0\27\7\1\0\37\7"+
    "\1\0\1\7\1\0\4\7\2\0\7\7\1\0\47\7\1\0\23\7"+
    "\16\0\11\10\56\0\125\7\14\0\u026c\7\2\0\10\7\12\0\32\7"+
    "\5\0\113\7\3\0\3\7\17\0\15\7\1\0\4\7\3\10\13\0"+
    "\22\7\3\10\13\0\22\7\2\10\14\0\15\7\1\0\3\7\1\0"+
    "\2\10\14\0\64\7\40\10\3\0\1\7\3\0\2\7\1\10\2\0"+
    "\12\10\41\0\3\10\2\0\12\10\6\0\130\7\10\0\51\7\1\10"+
    "\126\0\35\7\3\0\14\10\4\0\14\10\12\0\12\10\36\7\2\0"+
    "\5\7\u038b\0\154\7\224\0\234\7\4\0\132\7\6\0\26\7\2\0"+
    "\6\7\2\0\46\7\2\0\6\7\2\0\10\7\1\0\1\7\1\0"+
    "\1\7\1\0\1\7\1\0\37\7\2\0\65\7\1\0\7\7\1\0"+
    "\1\7\3\0\3\7\1\0\7\7\3\0\4\7\2\0\6\7\4\0"+
    "\15\7\5\0\3\7\1\0\7\7\17\0\4\10\32\0\5\10\20\0"+
    "\2\7\23\0\1\7\13\0\4\10\6\0\6\10\1\0\1\7\15\0"+
    "\1\7\40\0\22\7\36\0\15\10\4\0\1\10\3\0\6\10\27\0"+
    "\1\7\4\0\1\7\2\0\12\7\1\0\1\7\3\0\5\7\6\0"+
    "\1\7\1\0\1\7\1\0\1\7\1\0\4\7\1\0\3\7\1\0"+
    "\7\7\3\0\3\7\5\0\5\7\26\0\44\7\u0e81\0\3\7\31\0"+
    "\11\7\6\10\1\0\5\7\2\0\5\7\4\0\126\7\2\0\2\10"+
    "\2\0\3\7\1\0\137\7\5\0\50\7\4\0\136\7\21\0\30\7"+
    "\70\0\20\7\u0200\0\u19b6\7\112\0\u51a6\7\132\0\u048d\7\u0773\0\u2ba4\7"+
    "\u215c\0\u012e\7\2\0\73\7\225\0\7\7\14\0\5\7\5\0\1\7"+
    "\1\10\12\7\1\0\15\7\1\0\5\7\1\0\1\7\1\0\2\7"+
    "\1\0\2\7\1\0\154\7\41\0\u016b\7\22\0\100\7\2\0\66\7"+
    "\50\0\15\7\3\0\20\10\20\0\4\10\17\0\2\7\30\0\3\7"+
    "\31\0\1\7\6\0\5\7\1\0\207\7\2\0\1\10\4\0\1\7"+
    "\13\0\12\10\7\0\32\7\4\0\1\7\1\0\32\7\12\0\132\7"+
    "\3\0\6\7\2\0\6\7\2\0\6\7\2\0\3\7\3\0\2\7"+
    "\3\0\2\7\22\0\3\10\4\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\1\3\2\4\2\3\1\5\15\3"+
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25"+
    "\1\26\1\1\1\0\1\3\1\27\10\3\1\30\1\3"+
    "\1\30\11\3\1\31\3\3\1\0\1\32\1\0\1\33"+
    "\1\34\1\35\1\36\1\37\12\3\1\0\1\40\1\0"+
    "\5\3\1\41\2\3\1\42\1\43\1\26\3\3\2\0"+
    "\1\44\2\0\10\3\1\45\1\3\2\0\1\3\1\46"+
    "\2\3\1\47\7\3\1\50\1\0\1\51\1\0\6\3"+
    "\1\52\2\3\2\0\1\53\1\3\1\54\1\0\4\3"+
    "\1\55\1\56\1\57\1\60\5\3\1\61\1\3\1\62"+
    "\2\0\1\63\1\0\7\3\1\56\1\64\1\65\1\66"+
    "\4\0\4\3\1\0\2\3\4\0\4\3\4\0\1\3"+
    "\1\0\1\67\1\70\1\0\3\3\2\0\1\3\2\0"+
    "\1\71\1\72\1\73\2\0\1\3\1\74\1\0\1\75"+
    "\1\0\1\64\12\0\1\76\6\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[246];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\72\0\164\0\72\0\256\0\72\0\350\0\u0122"+
    "\0\u015c\0\u0196\0\u01d0\0\u020a\0\u0244\0\u027e\0\u02b8\0\u02f2"+
    "\0\u032c\0\u0366\0\u03a0\0\u03da\0\u0414\0\u044e\0\u0488\0\72"+
    "\0\72\0\72\0\72\0\72\0\72\0\72\0\u04c2\0\u04fc"+
    "\0\u0536\0\u0570\0\72\0\72\0\72\0\u05aa\0\72\0\72"+
    "\0\u05e4\0\u061e\0\u0658\0\72\0\u0692\0\u06cc\0\u0706\0\u0740"+
    "\0\u077a\0\u07b4\0\u07ee\0\u0828\0\u0862\0\u089c\0\u08d6\0\u0910"+
    "\0\u094a\0\u0984\0\u09be\0\u09f8\0\u0a32\0\u0a6c\0\u0aa6\0\u0ae0"+
    "\0\u0122\0\u0b1a\0\u0b54\0\u0b8e\0\u0bc8\0\u0c02\0\u0c3c\0\u0c76"+
    "\0\72\0\72\0\72\0\72\0\u0cb0\0\u0cea\0\u0d24\0\u0d5e"+
    "\0\u0d98\0\u0dd2\0\u0e0c\0\u0e46\0\u0e80\0\u0eba\0\u0ef4\0\u0122"+
    "\0\u0f2e\0\u0f68\0\u0fa2\0\u0fdc\0\u1016\0\u1050\0\u0122\0\u108a"+
    "\0\u10c4\0\u10fe\0\u1138\0\u0122\0\u1172\0\u11ac\0\u11e6\0\u1220"+
    "\0\u125a\0\72\0\u1294\0\u12ce\0\u1308\0\u1342\0\u137c\0\u13b6"+
    "\0\u13f0\0\u142a\0\u1464\0\u149e\0\u0122\0\u14d8\0\u1512\0\u154c"+
    "\0\u1586\0\u0122\0\u15c0\0\u15fa\0\u0122\0\u1634\0\u166e\0\u16a8"+
    "\0\u16e2\0\u171c\0\u1756\0\u1790\0\72\0\u17ca\0\72\0\u1804"+
    "\0\u183e\0\u1878\0\u18b2\0\u18ec\0\u1926\0\u1960\0\u0122\0\u199a"+
    "\0\u19d4\0\u1a0e\0\u1a48\0\u0122\0\u1a82\0\u0122\0\u1abc\0\u1af6"+
    "\0\u1b30\0\u1b6a\0\u1ba4\0\u0122\0\u1bde\0\72\0\72\0\u1c18"+
    "\0\u1c52\0\u1c8c\0\u1cc6\0\u1d00\0\u0122\0\u1d3a\0\u0122\0\u1d74"+
    "\0\u1dae\0\u0122\0\u1de8\0\u1e22\0\u1e5c\0\u1e96\0\u1ed0\0\u1f0a"+
    "\0\u1f44\0\u1f7e\0\u0122\0\u1fb8\0\u0122\0\u0122\0\u1ff2\0\u202c"+
    "\0\u2066\0\u20a0\0\u20da\0\u2114\0\u214e\0\u2188\0\u21c2\0\u21fc"+
    "\0\u2236\0\u2270\0\u22aa\0\u22e4\0\u231e\0\u2358\0\u2392\0\u23cc"+
    "\0\u2406\0\u2440\0\u247a\0\u24b4\0\u24ee\0\u2528\0\u2562\0\72"+
    "\0\72\0\u259c\0\u25d6\0\u2610\0\u264a\0\u2684\0\u26be\0\u26f8"+
    "\0\u2732\0\u276c\0\u0122\0\u0122\0\u0122\0\u27a6\0\u27e0\0\u281a"+
    "\0\72\0\u2854\0\72\0\u288e\0\u0122\0\u28c8\0\u2902\0\u293c"+
    "\0\u2976\0\u29b0\0\u29ea\0\u2a24\0\u2a5e\0\u2a98\0\u2ad2\0\72"+
    "\0\u2b0c\0\u2b46\0\u2b80\0\u2bba\0\u2bf4\0\u2c2e";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[246];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\2\4\1\5\1\6\1\7\1\10\1\2"+
    "\1\11\3\10\1\2\1\7\1\12\1\13\1\14\1\10"+
    "\1\15\1\10\1\16\1\17\1\10\1\20\1\21\2\10"+
    "\1\22\1\23\1\24\1\25\1\4\1\26\1\10\1\27"+
    "\4\10\1\30\1\31\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\1\43\1\44\1\45\1\46"+
    "\1\47\1\50\1\51\74\0\1\4\67\0\1\52\1\3"+
    "\1\4\1\52\11\5\1\52\1\5\1\52\20\5\1\52"+
    "\7\5\22\52\5\0\2\7\7\0\1\7\57\0\11\10"+
    "\1\0\1\10\1\0\20\10\1\0\7\10\26\0\6\10"+
    "\1\53\2\10\1\0\1\10\1\0\20\10\1\0\7\10"+
    "\41\0\1\54\56\0\11\10\1\0\1\10\1\0\1\10"+
    "\1\55\3\10\1\56\12\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\1\57\13\10\1\60\3\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\10\10\1\61"+
    "\7\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\1\62\1\10\1\63\15\10\1\0\3\10\1\64\3\10"+
    "\26\0\11\10\1\0\1\10\1\0\5\10\1\65\12\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\2\10"+
    "\1\66\12\10\1\67\2\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\1\10\1\70\6\10\1\71\6\10"+
    "\1\72\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\1\10\1\73\11\10\1\74\4\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\4\10\1\75\13\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\1\10\1\76"+
    "\6\10\1\77\6\10\1\100\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\11\10\1\101\6\10\1\0\5\10"+
    "\1\102\1\10\26\0\11\10\1\0\1\10\1\0\20\10"+
    "\1\0\6\10\1\103\26\0\11\10\1\0\1\10\1\0"+
    "\1\10\1\104\16\10\1\0\7\10\42\0\1\105\40\0"+
    "\1\106\30\0\1\107\40\0\1\110\70\0\1\111\72\0"+
    "\1\112\101\0\1\113\66\0\1\114\3\0\1\52\1\3"+
    "\1\4\67\52\4\0\6\10\1\115\2\10\1\0\1\10"+
    "\1\0\20\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\2\10\1\116\15\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\14\10\1\117\3\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\1\120\17\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\1\121\15\10"+
    "\1\122\1\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\20\10\1\0\4\10\1\123\2\10\26\0\11\10"+
    "\1\0\1\10\1\0\5\10\1\124\12\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\4\10\1\125\13\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\10\10"+
    "\1\126\7\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\20\10\1\127\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\14\10\1\130\3\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\20\10\1\131\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\1\132\17\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\2\10\1\133\15\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\13\10\1\134"+
    "\4\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\6\10\1\135\11\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\4\10\1\136\13\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\16\10\1\137\1\10\1\0"+
    "\5\10\1\140\1\10\26\0\11\10\1\0\1\10\1\0"+
    "\14\10\1\141\3\10\1\0\3\10\1\142\3\10\26\0"+
    "\11\10\1\0\1\10\1\0\2\10\1\143\15\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\7\10\1\144"+
    "\10\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\20\10\1\0\5\10\1\145\1\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\146\12\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\13\10\1\147\4\10\1\0"+
    "\7\10\47\0\1\150\64\0\1\151\37\0\1\152\36\0"+
    "\1\153\64\0\1\154\55\0\5\10\1\155\3\10\1\0"+
    "\1\10\1\0\20\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\3\10\1\156\14\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\14\10\1\157\3\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\7\10\1\160"+
    "\2\10\1\160\5\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\161\12\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\17\10\1\162\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\5\10\1\163\12\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\16\10"+
    "\1\164\1\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\16\10\1\165\1\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\15\10\1\166\2\10\1\0\7\10"+
    "\44\0\1\167\4\0\1\170\71\0\1\170\46\0\11\10"+
    "\1\0\1\10\1\0\15\10\1\171\2\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\7\10\1\172\10\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\1\10"+
    "\1\173\16\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\1\174\17\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\175\12\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\20\10\1\0\6\10\1\176"+
    "\26\0\11\10\1\0\1\10\1\0\13\10\1\177\4\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\10\10"+
    "\1\200\7\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\10\10\1\201\7\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\4\10\1\202\13\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\13\10\1\203\4\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\1\10"+
    "\1\204\16\10\1\0\7\10\66\0\1\205\52\0\1\206"+
    "\110\0\1\207\52\0\1\210\50\0\7\10\1\211\1\10"+
    "\1\0\1\10\1\0\20\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\4\10\1\212\13\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\10\10\1\213\7\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\10\10"+
    "\1\214\7\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\1\10\1\215\16\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\15\10\1\216\2\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\2\10\1\217\15\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\5\10"+
    "\1\220\12\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\14\10\1\221\3\10\1\0\7\10\47\0\1\222"+
    "\103\0\1\223\36\0\11\10\1\0\1\10\1\0\5\10"+
    "\1\224\12\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\1\225\17\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\226\12\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\20\10\1\227\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\10\10\1\230\7\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\16\10\1\231"+
    "\1\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\16\10\1\232\1\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\13\10\1\233\4\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\5\10\1\234\12\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\16\10\1\235"+
    "\1\10\1\0\7\10\66\0\1\236\71\0\1\237\31\0"+
    "\10\10\1\240\1\0\1\10\1\0\20\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\1\10\1\241\16\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\2\10"+
    "\1\242\15\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\11\10\1\243\6\10\1\0\7\10\26\0\11\10"+
    "\1\0\1\10\1\0\15\10\1\244\2\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\14\10\1\245\3\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\2\10"+
    "\1\246\15\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\15\10\1\247\2\10\1\0\7\10\63\0\1\250"+
    "\66\0\1\251\37\0\11\10\1\0\1\10\1\0\1\252"+
    "\17\10\1\0\7\10\56\0\1\253\41\0\11\10\1\0"+
    "\1\10\1\0\20\10\1\0\3\10\1\254\3\10\26\0"+
    "\11\10\1\0\1\10\1\0\10\10\1\255\7\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\10\10\1\256"+
    "\7\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\13\10\1\257\4\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\260\12\10\1\0\7\10\26\0"+
    "\5\10\1\261\3\10\1\0\1\10\1\0\20\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\3\10\1\262"+
    "\14\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\3\10\1\263\14\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\11\10\1\264\6\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\14\10\1\265\3\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\14\10\1\266"+
    "\3\10\1\0\7\10\62\0\1\267\52\0\1\270\117\0"+
    "\1\271\26\0\11\10\1\0\1\10\1\0\20\10\1\272"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\15\10\1\273"+
    "\2\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\15\10\1\274\2\10\1\0\7\10\26\0\11\10\1\0"+
    "\1\10\1\0\5\10\1\275\12\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\14\10\1\276\3\10\1\0"+
    "\7\10\26\0\11\10\1\277\1\10\1\0\20\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\5\10\1\300"+
    "\12\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\5\10\1\301\12\10\1\0\7\10\56\0\1\302\65\0"+
    "\1\303\62\0\1\304\100\0\1\305\45\0\11\10\1\0"+
    "\1\10\1\0\10\10\1\306\7\10\1\0\7\10\26\0"+
    "\11\10\1\0\1\10\1\0\10\10\1\307\7\10\1\0"+
    "\7\10\26\0\11\10\1\0\1\10\1\0\2\10\1\310"+
    "\15\10\1\0\7\10\26\0\11\10\1\0\1\10\1\0"+
    "\5\10\1\311\12\10\1\0\7\10\23\0\1\312\2\313"+
    "\34\0\1\313\32\0\1\314\2\315\11\10\1\0\1\10"+
    "\1\0\20\10\1\315\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\13\10\1\316\4\10\1\0\7\10\64\0\1\317"+
    "\51\0\1\320\103\0\1\321\57\0\1\322\53\0\11\10"+
    "\1\0\1\10\1\0\2\10\1\323\15\10\1\0\7\10"+
    "\26\0\11\10\1\0\1\10\1\0\2\10\1\324\15\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\20\10"+
    "\1\0\5\10\1\325\1\10\26\0\11\10\1\0\1\10"+
    "\1\0\13\10\1\263\4\10\1\0\7\10\24\0\1\313"+
    "\13\0\1\326\71\0\1\326\55\0\1\315\6\0\1\327"+
    "\71\0\1\327\64\0\11\10\1\0\1\10\1\0\5\10"+
    "\1\330\12\10\1\0\7\10\65\0\1\331\55\0\1\332"+
    "\46\0\11\10\1\0\1\10\1\0\3\10\1\333\14\10"+
    "\1\0\7\10\26\0\11\10\1\0\1\10\1\0\3\10"+
    "\1\334\14\10\1\0\7\10\26\0\11\10\1\0\1\10"+
    "\1\0\5\10\1\335\12\10\1\0\7\10\41\0\1\336"+
    "\64\0\1\337\63\0\11\10\1\0\1\10\1\0\2\10"+
    "\1\340\15\10\1\0\7\10\47\0\1\341\71\0\1\342"+
    "\51\0\1\343\76\0\1\344\63\0\11\10\1\0\1\10"+
    "\1\0\14\10\1\345\3\10\1\0\7\10\66\0\1\346"+
    "\36\0\1\347\105\0\1\350\57\0\1\351\105\0\1\352"+
    "\56\0\1\353\115\0\1\354\42\0\1\355\106\0\1\356"+
    "\60\0\1\357\116\0\1\360\30\0\1\361\2\362\34\0"+
    "\1\362\33\0\1\362\13\0\1\363\71\0\1\363\72\0"+
    "\1\364\100\0\1\365\62\0\1\366\73\0\1\343\50\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[11368];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\1\1\1\11\1\1\1\11\21\1\7\11"+
    "\4\1\3\11\1\1\2\11\1\1\1\0\1\1\1\11"+
    "\30\1\1\0\1\1\1\0\1\1\4\11\12\1\1\0"+
    "\1\1\1\0\16\1\2\0\1\11\2\0\12\1\2\0"+
    "\14\1\1\11\1\0\1\11\1\0\11\1\2\0\3\1"+
    "\1\0\6\1\2\11\10\1\2\0\1\1\1\0\13\1"+
    "\4\0\4\1\1\0\2\1\4\0\4\1\4\0\1\1"+
    "\1\0\2\11\1\0\3\1\2\0\1\1\2\0\3\1"+
    "\2\0\1\1\1\11\1\0\1\11\1\0\1\1\12\0"+
    "\1\11\6\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[246];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */

  private Symbol getSymbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }

  private Symbol getSymbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  private void print_error_msg(String message) {
     System.out.print("\nLex error:\t"+message+"\n");
  }



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public EssencePrimeLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public EssencePrimeLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1770) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzPushbackPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead < 0) {
      return true;
    }
    else {
      zzEndRead+= numRead;
      return false;
    }
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = zzLexicalState;


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2: 
          { /* ignore */
          }
        case 63: break;
        case 46: 
          { return getSymbol(sym.LETTING);
          }
        case 64: break;
        case 16: 
          { return getSymbol(sym.NOT);
          }
        case 65: break;
        case 30: 
          { return getSymbol(sym.AND);
          }
        case 66: break;
        case 60: 
          { return getSymbol(sym.BE_NEW_TYPE);
          }
        case 67: break;
        case 20: 
          { return getSymbol(sym.DIVISION);
          }
        case 68: break;
        case 14: 
          { return getSymbol(sym.GREATER);
          }
        case 69: break;
        case 34: 
          { return getSymbol(sym.MAX);
          }
        case 70: break;
        case 28: 
          { return getSymbol(sym.IF);
          }
        case 71: break;
        case 50: 
          { return getSymbol(sym.EXISTS);
          }
        case 72: break;
        case 11: 
          { return getSymbol(sym.COLON);
          }
        case 73: break;
        case 8: 
          { return getSymbol(sym.LBRACK);
          }
        case 74: break;
        case 31: 
          { return getSymbol(sym.OR);
          }
        case 75: break;
        case 15: 
          { return getSymbol(sym.EQ);
          }
        case 76: break;
        case 27: 
          { return getSymbol(sym.GEQ);
          }
        case 77: break;
        case 6: 
          { return getSymbol(sym.LPAREN);
          }
        case 78: break;
        case 42: 
          { return getSymbol(sym.GIVEN);
          }
        case 79: break;
        case 61: 
          { return getSymbol(sym.HEADER);
          }
        case 80: break;
        case 17: 
          { return getSymbol(sym.PLUS);
          }
        case 81: break;
        case 51: 
          { return getSymbol(sym.FORALL);
          }
        case 82: break;
        case 13: 
          { return getSymbol(sym.LESS);
          }
        case 83: break;
        case 39: 
          { return getSymbol(sym.TRUE);
          }
        case 84: break;
        case 29: 
          { return getSymbol(sym.NEQ);
          }
        case 85: break;
        case 57: 
          { return getSymbol(sym.MAXIMISING);
          }
        case 86: break;
        case 36: 
          { return getSymbol(sym.IFF);
          }
        case 87: break;
        case 56: 
          { return getSymbol(sym.SUCH_THAT);
          }
        case 88: break;
        case 47: 
          { return getSymbol(sym.LEXLEQ);
          }
        case 89: break;
        case 24: 
          { return getSymbol(sym.BE);
          }
        case 90: break;
        case 18: 
          { return getSymbol(sym.MINUS);
          }
        case 91: break;
        case 19: 
          { return getSymbol(sym.MULT);
          }
        case 92: break;
        case 3: 
          { return getSymbol(sym.IDENTIFIER, yytext());
          }
        case 93: break;
        case 52: 
          { return getSymbol(sym.ALLDIFF);
          }
        case 94: break;
        case 49: 
          { return getSymbol(sym.ATMOST);
          }
        case 95: break;
        case 55: 
          { return getSymbol(sym.BE_DOMAIN);
          }
        case 96: break;
        case 53: 
          { return getSymbol(sym.ATLEAST);
          }
        case 97: break;
        case 62: 
          { return getSymbol(sym.MATRIX_INDEXED_BY);
          }
        case 98: break;
        case 44: 
          { return getSymbol(sym.TABLE);
          }
        case 99: break;
        case 54: 
          { return getSymbol(sym.ELEMENT);
          }
        case 100: break;
        case 59: 
          { return getSymbol(sym.OCCURRENCE);
          }
        case 101: break;
        case 10: 
          { return getSymbol(sym.COMMA);
          }
        case 102: break;
        case 35: 
          { return getSymbol(sym.MIN);
          }
        case 103: break;
        case 21: 
          { return getSymbol(sym.POWER);
          }
        case 104: break;
        case 25: 
          { return getSymbol(sym.OF);
          }
        case 105: break;
        case 9: 
          { return getSymbol(sym.RBRACK);
          }
        case 106: break;
        case 43: 
          { return getSymbol(sym.FALSE);
          }
        case 107: break;
        case 23: 
          { return getSymbol(sym.DOTDOT);
          }
        case 108: break;
        case 40: 
          { return getSymbol(sym.LEXLESS);
          }
        case 109: break;
        case 7: 
          { return getSymbol(sym.RPAREN);
          }
        case 110: break;
        case 58: 
          { return getSymbol(sym.MINIMISING);
          }
        case 111: break;
        case 37: 
          { return getSymbol(sym.ENUM);
          }
        case 112: break;
        case 48: 
          { return getSymbol(sym.LEXGEQ);
          }
        case 113: break;
        case 41: 
          { return getSymbol(sym.LEXGREATER);
          }
        case 114: break;
        case 45: 
          { return getSymbol(sym.WHERE);
          }
        case 115: break;
        case 38: 
          { return getSymbol(sym.FIND);
          }
        case 116: break;
        case 5: 
          { return getSymbol(sym.DOT);
          }
        case 117: break;
        case 22: 
          { return getSymbol(sym.MODULO);
          }
        case 118: break;
        case 12: 
          { return getSymbol(sym.BAR);
          }
        case 119: break;
        case 26: 
          { return getSymbol(sym.LEQ);
          }
        case 120: break;
        case 4: 
          { return getSymbol(sym.INTEGER, new Integer(yytext()));
          }
        case 121: break;
        case 1: 
          { print_error_msg("Illegal character \""+yytext()+
                                                              "\" at line "+yyline+", column "+yycolumn); 
                                                              //System.exit(0);
          }
        case 122: break;
        case 33: 
          { return getSymbol(sym.SUM);
          }
        case 123: break;
        case 32: 
          { return getSymbol(sym.INT);
          }
        case 124: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
