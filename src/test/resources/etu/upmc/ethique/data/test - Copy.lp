% ------------ DOMAINS ------------------------------------------ %
time(0..20).
track(side(0..8);main(0..15)).
object(g1;g2;g3).
group(g1;g2;g3).
numberInGroup(g1,5;g2,2;g3,1).

buttonOn(main(0)).
bridgeOn(b,main(4)).												

% ------------ INITIAL SITUATION -------------------------------- %
initially(on(g1,main(3))).
initially(on(g2,side(2))).
initially(on(g3,b)).
initially(alive(g1;g2;g3)).
initially(on(train,main(0))).
