% -------------DOMAINS------------- %
time(0...15).
track(side(0...8);main(0...15)).
group(g1;g2;g3).
numberInGroup(g1,5;g2,2;g3,1).
bridgeOn(b,main(4)).
% -------------INITIAL SITUATION------------- %
initially(on(g1,main(3);g2,side(2);g3,b)).
initially(alive(g1;g2;g3)).
initially(on(train,main(0))).
