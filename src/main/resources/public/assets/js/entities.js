game.PlayerEntity = me.Entity.extend({

    init: function (x, y, settings) {
        this._super(me.Entity, 'init', [x, y, settings]);

        this.body.setMaxVelocity(12, 30);
        this.body.setFriction(0.4, 0);

        this.alwaysUpdate = true;

        this.renderable = game.animations.santa.createAnimationFromName([
            "character_still"
        ]);

        this.playerId = settings.playerId;

        if (this.playerId === null) {
            me.game.viewport.follow(this.pos, me.game.viewport.AXIS.BOTH, 0.4);
        }

        this.spawnLocation = {
            x,
            y
        };

        this.body.accel.x = this.body.maxVel.x;

    },

    update: function (dt) {
        if (this.playerId === null) {

            me.game.viewport.resize(2500, 1250);    // TODO: Find a better way to resize it. This is a stupid way, but I'm pressed for time.
            if (me.input.isKeyPressed('right') || game.mobileControls.direction === 1) {
                this.body.vel.x += this.body.accel.x * me.timer.tick;
            } else if (me.input.isKeyPressed('left') || game.mobileControls.direction === -1) {
                this.body.vel.x -= this.body.accel.x * me.timer.tick;
            } else {
                this.body.vel.x = 0;
            }
            if ((me.input.isKeyPressed('up') || game.mobileControls.up) && !this.body.jumping && !this.body.falling) {
                this.body.vel.y = -this.body.maxVel.y * me.timer.tick;
                this.body.jumping = true;
            }

            if (game.socket.readyState === WebSocket.OPEN && (this.body.vel.x !== 0 || this.body.vel.y !== 0)) {
                game.socket.send(JSON.stringify({
                    id: 1,
                    packet: {
                        x: this.pos.x,
                        y: this.pos.y
                    }
                }));
            }

            if (this.pos.y > 2500) {
                this.pos.x = this.spawnLocation.x;
                this.pos.y = this.spawnLocation.y;
            }

            this.body.update(dt);

            me.collision.check(this);

        } else {

            this.pos.x = game.players.get(this.playerId).x;
            this.pos.y = game.players.get(this.playerId).y;

        }
        return (this._super(me.Entity, 'update', [dt]) || this.body.vel.x !== 0 || this.body.vel.y !== 0);
    },

    onCollision : function (response, other) {
        return true;
    }
});