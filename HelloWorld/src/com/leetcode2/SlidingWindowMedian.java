package com.leetcode2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/sliding-window-median/description/
 * @Diary: Good lesson about memory limit awareness:
 *      Integer.max - Integer.min results in out of bound, better to convert each of them to double then perform subtraction
 *      Pay attention to details of bound limit that might happen for < Integer.min and > Integer.max
 */
public class SlidingWindowMedian {

    public static void main(String[] args) {
        SlidingWindowMedian p = new SlidingWindowMedian();

        int[] nums = {1,3,-1,-3,5,3,6,7}; int k = 3;
        //int[] nums = {2147483647,2147483647}; int k = 2;
        //int[] nums = {3}; int k = 1;
        //int[] nums = {1,4,2,3}; int k = 4;
        //int[] nums = {2147483647,1,2,3,4,5,6,7,2147483647}; int k = 2;
        //int[] nums = {591304913,654838122,803604157,650410716,761140582,77160142,336340962,498568008,744298440,331637305,108121170,214317406,495545201,479588719,734451620,985352962,580830317,347457135,704031752,26760894,946263235,729784610,3810831,210084363,217751851,236203847,121255451,564341110,238745549,96989447,954526234,19571748,170349223,256668088,318886771,558260932,128408959,741287256,262275345,70774702,951959723,626872889,90851819,878627494,994734886,349037107,488817392,74452500,487684946,368785401,538429365,33732744,596502756,761134474,619997917,714335775,407783695,793222866,911206864,947876491,926490791,126799940,822813756,383594059,112419897,448578097,584475309,479295563,102346022,788169085,78676899,265693519,892471720,746407392,62550148,160834053,248995776,573862876,357276833,180433797,96895193,517882903,99708008,548224374,95186766,514616303,32536630,820767581,353269186,395903725,49567669,653136425,474975158,503743185,824152399,52825421,719083114,384910260,965995056,502534872,50410053,133203853,872175775,727854581,985089555,448716162,402945048,281482745,766999452,762940470,908601631,111398400,814652263,297829547,984298919,838377370,761228201,47783959,93598582,591811579,580438996,212976029,780363501,679707656,26550930,712365581,299966420,36996362,175082151,346094045,62393170,669988454,239185157,688525093,399348015,748669808,579753861,582813966,474391173,278641878,619693086,678987030,6913052,223548026,222774379,897969710,454823432,323121951,517465772,885943301,190430187,809518879,274895608,726137537,836997036,391296202,713318478,135437123,106544088,271082574,64984509,711423596,854915123,538368762,15178123,341137846,869923879,534944155,85361674,154578722,696851431,399168657,88704971,510796579,465966194,413940527,398904656,739586036,587158216,680778347,48806813,104836584,490350257,422015860,475051557,994802600,132601236,688431513,619527533,398526475,825948910,389036162,590553266,552303806,145745108,52167507,605962173,38787537,7567896,78050055,260723224,901564466,742345408,866766833,14079561,411980557,669943571,484836576,95376114,756525914,473341289,167615735,412091859,393112638,384408694,919088460,62852927,600168343,298650842,755418455,394652121,488695711,176843580,92681612,774208809,524035690,637405483,225521545,824948438,753972434,861180938,621222764,972986481,688792840,604404550,422600118,704741175,5593598,108278274,707280687,737498842,660405588,241229820,693437782,215049805,127094734,482449220,418768046,930637903,107834620,693238850,166566975,112935362,315042342,14947070,107302438,131769142,381308115,347264735,410827177,612438734,368682267,952539874,994557580,309517390,853380696,873563006,420725881,623716043,720832272,865721355,770083638,704741975,226060620,295247375,180901986,730318197,234389305,896040537,597972595,694914783,416685495,81920176,88358883,929525082,377499827,972899151,575542599,878115305,976308951,49476377,59254006,35623790,374079595,471118396,107653661,946828231,281232725,859880606,595884379,105490470,742794024,816721357,67859122,780404053,213781473,285075280,224214503,686835083,705615934,675282582,816259857,753879563,300298041,522604637,208017829,52274687,259852786,511519951,531756094,190711622,244629630,999469630,261963154,266231506,137463219,237874717,138796483,585249139,807175913,551371692,512090639,740396144,110720068,589818083,288006429,95911865,377979805,242933387,408000940,149492287,753979197,974846679,888369568,166510363,368478900,829517999,38151447,54221501,206174488,283497205,264290320,931226244,258663572,851753076,287957430,419869319,905358969,446552988,553701629,16636152,223911132,676024558,401748607,500251681,331524562,360733216,289804409,42769245,208657548,66613685,737223708,685700813,969292867,887248105,999939614,571049654,527116335,874198470,718056163,304813979,260046958,272980039,743424059,461279945,109048523,767953748,414902744,183995177,823466737,620827491,417579042,274400498,201779777,229012004,507034382,304725556,566900175,653783133,252273210,823121292,109900670,264624270,101472953,353905353,705049328,742270128,601535873,807891082,479293771,279249300,91216405,563773455,642607621,611025384,236828934,96695847,314958328,104912488,766068785,946584308,476586158,662532774,485622923,426608261,350123872,426723924,501093335,237796389,176842856,80513344,273074998,398937747,502767895,819343967,841887383,624474576,791615943,27960836,431830537,428592146,481024362,93499757,639869942,488989596,28222903,895928381,505945281,534579294,395593788,135423804,882691655,374590687,116601983,224442217,12035565,4235293,901566256,772429938,691321851,159819487,377054190,73012680,349423832,546053526,116966429,345708707,382973414,418657617,15119925,155982638,670147526,410719545,950951357,66156125,282442307,68993879,731933551,820861641,786651959,350143981,557674465,9076325,660412494,2793893,859803064,85614563,902895429,844025501,407106872,155276940,339878053,816914329,16172232,16742280,653099359,644985474,566390040,669878776,188805589,422187704,215750018,951134968,650117486,127791266,304160662,17166374,545417698,20539821,615388027,338304415,138584227,312829841,484148409,59750158,139020935,648603765,249464761,659137761,53192832,658730272,1481219,65506194,889448803,332365254,457858131,787700516,793372304,275327683,398087474,244615113,755482411,115055544,3245708,656501759,847063805,932274672,699723792,637321172,955990215,647875229,96883513,321577143,323681480,538556509,23158305,113049184,80565049,935059511,52851209,5018383,592100848,147215785,940494307,53670760,101331580,125332989,937572063,331639733,148928566,16938585,657430200,626007585,787094442,197021174,62571391,956338663,416294893,959523303,253448198,36770363,110157761,83563991,798670177,476871089,156400797,901189829,95293712,726100569,215675860,59266531,246131465,471006711,378047513,233418096,752800050,129770804,362001123,124680888,360623722,804043820,597375816,402268365,438868377,247463372,588552612,287050380,444076,459607900,131297041,243662618,779284475,78891919,377545943,747970763,568322781,941202058,211423582,103685467,32406152,129812551,502116461,253605895,746721617,48762429,4369627,425876991,151592286,895945460,13456,226154992,88379001,913143239,280276411,966980384,676051970,69483513,725782670,315198308,488783985,867486120,572739357,8667245,434477297,815530879,386848199,117659702,260129783,517536167,921588419,484495969,458740137,579189829,47567799,46554618,759417218,29868805,286810815,476063837,473818315,595057129,293823024,218659915,465649966,541547472,545644496,684850160,557269778,856974279,141370371,333385324,422305445,254160780,335255577,790876558,131513954,594352115,329554608,256949621,755644608,48121945,770151852,58236095,314484211,578879010,118600160,241043282,853260910,653298282,47822110,25792301,846989860,482459635,948318020,550312684,40696006,516446605,936672708,248565777,789320624,105240049,35956993,886276544,703299416,603291624,252027081,774377061,997341985,9855638,873424203,235347507,968156022,107647013,835095295,286484851,292554183,368085698,308917857,514447800,347990356,868921089,860921801,560874499,311978010,52326674,133598295,59111528,788479691,4064647,181002581,66513293,635892520,566956168,240352415,979277476,401868524,368213053,656394764,196282487,177855795,718088519,55642693,31354806,639709505,87492231,41585378,786241349,684451230,294904209,62783387,785914632,842790974,778742984,567987270,390214553,713912911,743159388,514943164,290659938,743764688,743780607,218352662,951121158,418012816,122389175,498508977,106669492,438285377,393422029,141892290,76869860,107043751,78984027,132626521,758891439,783035740,701893364,594095777,316249136,970181005,136303011,65611684,869440655,3332975,768693159,957281539,83342649,580563899,519242172,322621974,58791990,860455466,313116742,11126222,753293021,972683460,38369834,75179447,820581293,369810417,586004101,610920365,619258248,167620774,496782332,4234388,300398765,67989258,234159002,320305310,422820719,324436310,340082437,311533992,179650736,820890848,277534008,972569750,481750933,567560319,656900037,305492632,928749694,230455593,354136010,79712811,289375905,283353458,350323207,274958553,991075574,916984664,64091907,303373802,470290814,83384869,290155439,500079514,732881087,359208595,642324448,146704067,344027313,41071867,745596560,467282253,58107670,303528883,136274956,948596368,933540226,495053400,10845322,534195437,741065199,470625571,621699896,402209417,448129341,484684158,681170435,196178888,791172471,16977873,879270107,850691920,425956292,484404193,63744552,553324189,115453013,34034828,231177603,604056198,217320417,427543550,244161988,936766546,825701043,311081365,14799688,777736811,482601766,42146443,833147638,911952004,379521167,378800757,8288122,859513046,506249331,202296703,534074120,849574027,964881464,113746951,277538205,43108729,619397842,11788416,559412188,357237150,867986685,188779302,773360673,94778045,87693397,482120115,329951252,480894388,56510386,582285528,389889717,903866622,134480723,503188326,295593196,905169661,412337079,219557884,732450842,925036890,460889597,990920628,667312311,358406343,836755544,627507452,18533925,907527238,381428072,215898387,149805457,929481515,999774327,295059761,322640782,20392977,87843144,496929228,325631813,105548535,925713901,101995239,127988723,913810323,754538964,438410991,150111458,422967959,651615343,679953748,15135527,418205452,65055133,312444008,648925541,569608121,714569899,47738469,122026730,642325381,955363576,60393213,416449507,410637154,359184398,235645469,533552415,671212680,132893647,953510827,133495475,331015788,399703186,275577864,307912247,793029706,959734038,293282627,519120702,428559331,136524079,47660757,815921146,503614727,812642440,878472738,541234441,934004842,506898502,375495465,645325369,984038011,748263908,200243502,178641843,44295373,88487080,143669836,680293002,274526564,966065970,339881401,80205587,543054040,96729108,873975955,125730205,23646787}; int k = 414;
        //int[] nums = {-2147483648,-2147483648,2147483647,-2147483648,-2147483648,-2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648}; int k = 3;

        System.out.println(Arrays.toString(p.medianSlidingWindow(nums, k)));
        //QuickTest();
    }

    private static void QuickTest() {
        String actual   = "-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,2147483647.00000,2147483647.00000,2147483647.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000";
        String expected = "-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,-2147483648.00000,2147483647.00000,2147483647.00000,2147483647.00000,2147483647.00000,2147483647.00000,-2147483648.00000";

        String[] a = expected.split(",");
        String[] b = actual.split(",");
        for (int i=0; i<a.length; i++) {
            if (!a[i].equals(b[i])) {
                System.out.println(i + ": expected = " + a[i] + "; Actual = " + b[i]);
            }
        }
    }


    static class MyMedian {
        final PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>((a, b) -> {
            if (a < b) return +1;
            if (a==b) return 0;
            return -1;
        });
        final PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        Integer midValue = null;  // in case odd elements; always keep 2 queues above balance

        final Map<Integer, Integer> removedValToCount = new HashMap<>();
        int totalRemoveCount = 0;

        int size() {
            return maxHeap.size() + minHeap.size() + (midValue != null ?1 :0) - totalRemoveCount;
        }

        boolean isEmpty() {
            return size() == 0;
        }

        public void addAndBalance(int num) {
            if (isEmpty()) {
                midValue = num;
                return;
            }

            if (midValue != null) {
                if (num <= midValue) {
                    maxHeap.add(num);
                    minHeap.add(midValue);
                } else {
                    minHeap.add(num);
                    maxHeap.add(midValue);
                }
                midValue = null;
            } else {
                if (maxHeap.peek() != null && num <= maxHeap.peek()) {
                    maxHeap.add(num);
                    midValue = maxHeap.remove();
                } else {
                    minHeap.add(num);
                    midValue = minHeap.remove();
                }
            }

            cleanupAsNeeded();
        }

        private void cleanupAsNeeded() {
            removeGhostVals(maxHeap);
            removeGhostVals(minHeap);
        }

        private void removeGhostVals(PriorityQueue<Integer> heap) {
            if (heap.peek() == null) return;
            int cur = heap.peek();
            while (removedValToCount.containsKey(cur)) {
                cur = heap.remove();
                removedValToCount.put(cur, removedValToCount.get(cur) - 1);
                --totalRemoveCount;

                if (removedValToCount.get(cur) <= 0) {
                    removedValToCount.remove(cur);
                }

                if (heap.peek() == null) break;
                cur = heap.peek();
            }
        }

        public double getMedian() {
            if (isEmpty()) return 0.0;
            if (midValue != null) return midValue;
            return memLimitCompute(minHeap.peek(), maxHeap.peek());
        }

        private double memLimitCompute(int a, int b) {
            if (a == b) return a;

            int min = Math.min(a, b);
            int max = Math.max(a, b);
            return (double)min + ((double)max - (double)min)/2.0;
        }

        public void slideRemove(int num) {
            addRemoveCount(num);

            if (midValue != null) {
                if (num <= midValue) {
                    // remove from left
                    maxHeap.add(midValue);
                } else {
                    // remove from right
                    minHeap.add(midValue);
                }
                midValue = null;
            } else {
                if (maxHeap.peek() != null && num <= maxHeap.peek()) {
                    // remove from left
                    midValue = minHeap.remove();
                } else {
                    // remove from right
                    midValue = maxHeap.remove();
                }
            }

            cleanupAsNeeded();
        }

        private void addRemoveCount(int num) {
            int count = removedValToCount.containsKey(num) ?removedValToCount.get(num) :0 ;
            removedValToCount.put(num, count+1);
            ++totalRemoveCount;
        }
    }

    /**
     * Solution: use 2 heap to keep track of median of the first window [0..k-1], start sliding from this window:
     * - when removing an element from the left you know whether it's smaller or bigger than the current median, hence
     *      adjust the 2 heaps accordingly
     * - similarly when adding new element from the right, by comparing it with the median, you know whether this new
     *      element should be add to the right or left. So adjust the 2 heaps accordingly
     * Keep doing so until finish
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        MyMedian med = new MyMedian();
        double[] result = new double[n-k + 1];

        // init with first window first
        for (int i=0; i<k; i++) {
            med.addAndBalance(nums[i]);
        }
        result[0] = med.getMedian();

        // now slide the window
        for (int i=1; i<n-k + 1; i++) {
            med.slideRemove(nums[i-1]);
            med.addAndBalance(nums[i+k - 1]);

            // the end
            result[i] = med.getMedian();
        }

        return result;
    }
}